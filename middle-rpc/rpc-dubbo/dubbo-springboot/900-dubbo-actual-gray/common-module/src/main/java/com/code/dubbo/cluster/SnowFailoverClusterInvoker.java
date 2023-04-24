/*
 * Copyright (C) <2023> <Snow>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.code.dubbo.cluster;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.support.FailoverClusterInvoker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.dubbo.common.constants.CommonConstants.DEFAULT_RESELECT_COUNT;
import static org.apache.dubbo.common.constants.LoggerCodeConstants.CLUSTER_FAILED_RESELECT_INVOKERS;
import static org.apache.dubbo.rpc.cluster.Constants.CLUSTER_STICKY_KEY;
import static org.apache.dubbo.rpc.cluster.Constants.DEFAULT_CLUSTER_STICKY;

/**
 * @author Snow
 * @date 2023/4/1 12:08
 */
@Slf4j
public class SnowFailoverClusterInvoker<T> extends FailoverClusterInvoker<T> {

	private volatile Invoker<T> stickyInvoker = null;

	private volatile boolean enableConnectivityValidation = true;

	private volatile int reselectCount = DEFAULT_RESELECT_COUNT;

	public SnowFailoverClusterInvoker(Directory<T> directory) {
		super(directory);
	}

	@Override
	protected Invoker<T> select(LoadBalance loadbalance, Invocation invocation, List<Invoker<T>> invokers, List<Invoker<T>> selected) throws RpcException {
		if (CollectionUtils.isEmpty(invokers)) {
			return null;
		}
		String methodName = invocation == null ? StringUtils.EMPTY_STRING : invocation.getMethodName();

		boolean sticky = invokers.get(0).getUrl().getMethodParameter(methodName, CLUSTER_STICKY_KEY, DEFAULT_CLUSTER_STICKY);

		// ignore overloaded method
		if (stickyInvoker != null && !invokers.contains(stickyInvoker)) {
			stickyInvoker = null;
		}
		// ignore concurrency problem
		if (sticky && stickyInvoker != null && (selected == null || !selected.contains(stickyInvoker))) {
			if (availableCheck && stickyInvoker.isAvailable()) {
				return stickyInvoker;
			}
		}

		Invoker<T> invoker = doSelect(loadbalance, invocation, invokers, selected);

		if (sticky) {
			stickyInvoker = invoker;
		}

		return invoker;
	}

	private Invoker<T> doSelect(LoadBalance loadbalance, Invocation invocation, List<Invoker<T>> invokers, List<Invoker<T>> selected) throws RpcException {
		if (CollectionUtils.isEmpty(invokers)) {
			return null;
		}

		Invoker<T> invoker = loadbalance.select(invokers, getUrl(), invocation);

		// If the `invoker` is in the  `selected` or invoker is unavailable && availablecheck is true, reselect.
		boolean isSelected = selected != null && selected.contains(invoker);
		boolean isUnavailable = availableCheck && !invoker.isAvailable() && getUrl() != null;

		if (isUnavailable) {
			invalidateInvoker(invoker);
		}
		if (isSelected || isUnavailable) {
			try {
				Invoker<T> rInvoker = reselect(loadbalance, invocation, invokers, selected, availableCheck);
				if (rInvoker != null) {
					invoker = rInvoker;
				} else {
					// Check the index of current selected invoker, if it's not the last one, choose the one at index+1.
					int index = invokers.indexOf(invoker);
					try {
						// Avoid collision
						invoker = invokers.get((index + 1) % invokers.size());
					} catch (Exception e) {
						log.warn(CLUSTER_FAILED_RESELECT_INVOKERS, "select invokers exception", "", e.getMessage() + " may because invokers list dynamic change, ignore.", e);
					}
				}
			} catch (Throwable t) {
				log.error(CLUSTER_FAILED_RESELECT_INVOKERS, "failed to reselect invokers", "", "cluster reselect fail reason is :" + t.getMessage() + " if can not solve, you can set cluster.availablecheck=false in url", t);
			}
		}

		return invoker;
	}

	private void invalidateInvoker(Invoker<T> invoker) {
		if (enableConnectivityValidation) {
			if (getDirectory() != null) {
				getDirectory().addInvalidateInvoker(invoker);
			}
		}
	}



	/**
	 * Reselect, use invokers not in `selected` first, if all invokers are in `selected`,
	 * just pick an available one using loadbalance policy.
	 *
	 * @param loadbalance    load balance policy
	 * @param invocation     invocation
	 * @param invokers       invoker candidates
	 * @param selected       exclude selected invokers or not
	 * @param availableCheck check invoker available if true
	 * @return the reselect result to do invoke
	 * @throws RpcException exception
	 */
	private Invoker<T> reselect(LoadBalance loadbalance, Invocation invocation,
								List<Invoker<T>> invokers, List<Invoker<T>> selected, boolean availableCheck) throws RpcException {

		// Allocating one in advance, this list is certain to be used.
		List<Invoker<T>> reselectInvokers = new ArrayList<>(Math.min(invokers.size(), reselectCount));

		// 1. Try picking some invokers not in `selected`.
		//    1.1. If all selectable invokers' size is smaller than reselectCount, just add all
		//    1.2. If all selectable invokers' size is greater than reselectCount, randomly select reselectCount.
		//            The result size of invokers might smaller than reselectCount due to disAvailable or de-duplication (might be zero).
		//            This means there is probable that reselectInvokers is empty however all invoker list may contain available invokers.
		//            Use reselectCount can reduce retry times if invokers' size is huge, which may lead to long time hang up.
		if (reselectCount >= invokers.size()) {
			for (Invoker<T> invoker : invokers) {
				// check if available
				if (availableCheck && !invoker.isAvailable()) {
					// add to invalidate invoker
					invalidateInvoker(invoker);
					continue;
				}

				if (selected == null || !selected.contains(invoker)) {
					reselectInvokers.add(invoker);
				}
			}
		} else {
			for (int i = 0; i < reselectCount; i++) {
				// select one randomly
				Invoker<T> invoker = invokers.get(ThreadLocalRandom.current().nextInt(invokers.size()));
				// check if available
				if (availableCheck && !invoker.isAvailable()) {
					// add to invalidate invoker
					invalidateInvoker(invoker);
					continue;
				}
				// de-duplication
				if (selected == null || !selected.contains(invoker) || !reselectInvokers.contains(invoker)) {
					reselectInvokers.add(invoker);
				}
			}
		}

		// 2. Use loadBalance to select one (all the reselectInvokers are available)
		if (!reselectInvokers.isEmpty()) {
			return loadbalance.select(reselectInvokers, getUrl(), invocation);
		}

		// 3. reselectInvokers is empty. Unable to find at least one available invoker.
		//    Re-check all the selected invokers. If some in the selected list are available, add to reselectInvokers.
		if (selected != null) {
			for (Invoker<T> invoker : selected) {
				if ((invoker.isAvailable()) // available first
						&& !reselectInvokers.contains(invoker)) {
					reselectInvokers.add(invoker);
				}
			}
		}

		// 4. If reselectInvokers is not empty after re-check.
		//    Pick an available invoker using loadBalance policy
		if (!reselectInvokers.isEmpty()) {
			return loadbalance.select(reselectInvokers, getUrl(), invocation);
		}

		// 5. No invoker match, return null.
		return null;
	}

}
