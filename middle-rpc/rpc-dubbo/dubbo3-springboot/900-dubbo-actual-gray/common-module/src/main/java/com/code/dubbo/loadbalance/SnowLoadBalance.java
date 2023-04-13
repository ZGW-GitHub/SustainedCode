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

package com.code.dubbo.loadbalance;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Snow
 * @date 2023/3/31 23:46
 */
@Slf4j
public class SnowLoadBalance extends RandomLoadBalance {

	public static final String APP_ENVIRONMENT_KEY = "appEnvironment";

	public static final String APP_ENVIRONMENT_DEFAULT = "prod";

	@Override
	public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) {
		Invoker<T> invoker = doSelect(invokers, url, invocation);

		if (Objects.isNull(invoker)) {
			throw new RpcException(RpcException.NO_INVOKER_AVAILABLE_AFTER_FILTER, "Failed to invoke the method "
					+ invocation.getMethodName() + " in the service " + invocation.getServiceName()
					+ ". No provider available for the service " + invocation.getTargetServiceUniqueName()
					+ " on the consumer " + NetUtils.getLocalHost()
					+ " using the dubbo version " + Version.getVersion()
					+ ". Please check if the providers have been started and registered.");
		}
		return invoker;
	}

	@Override
	protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
		log.debug("SnowLoadBalance >> 开始过滤 ：\ninvokers = {}\nrpcContextAttachment = {}", JSONUtil.toJsonStr(invokers), JSONUtil.toJsonStr(RpcContext.getServerAttachment()));

		String appEnvironment = getAppEnvironment();

		final List<Invoker<T>> invokerList = invokers.stream().filter(invoker -> isMatch(invoker, appEnvironment)).toList();
		log.debug("SnowLoadBalance >> 过滤完成 ：\nbefore = {}\nafter = {}", JSONUtil.toJsonStr(invokers), JSONUtil.toJsonStr(invokerList));

		if (CollectionUtils.isEmpty(invokerList)) {
			return null;
		}
		return super.doSelect(invokerList, url, invocation);
	}

	private <T> boolean isMatch(Invoker<T> invoker, String appEnvironment) {
		if (Objects.isNull(invoker)) {
			return false;
		}

		URL url = invoker.getUrl();
		if (Objects.isNull(url)) {
			return false;
		}

		String urlAppEnvironment = url.getParameter(APP_ENVIRONMENT_KEY);
		if (StrUtil.isBlank(urlAppEnvironment)) {
			return false;
		}
		return StrUtil.equalsIgnoreCase(urlAppEnvironment, appEnvironment);
	}

	private String getAppEnvironment() {
		// [当前服务]获取从 Consumer 传递来的参数
		String appEnvironment = RpcContext.getServerAttachment().getAttachment(APP_ENVIRONMENT_KEY);
		if (StrUtil.isNotBlank(appEnvironment)) {
			log.debug("SnowLoadBalance >> 从上下文中取得 appEnvironment = {}", appEnvironment);
			return appEnvironment;
		}

		// 没有获取到传递来的参数，说明[当前服务]为 Consumer ，则从系统属性中获取所属环境
		appEnvironment = System.getProperty(APP_ENVIRONMENT_KEY);
		if (StrUtil.isNotBlank(appEnvironment)) {
			log.debug("SnowLoadBalance >> 从环境变量中取得 appEnvironment = {}", appEnvironment);
		}
		if (StrUtil.isBlank(appEnvironment)) {
			appEnvironment = APP_ENVIRONMENT_DEFAULT;
		}
		RpcContext.getClientAttachment().setAttachment(APP_ENVIRONMENT_KEY, appEnvironment);
		return appEnvironment;
	}

}
