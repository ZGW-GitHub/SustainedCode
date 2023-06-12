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

package com.code.framework.web.component.shutdown;

import cn.hutool.core.collection.CollUtil;
import com.code.framework.basic.trace.thread.TraceThreadPoolExecutor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2023/6/12 21:14
 */
@Slf4j
@Component
public class CommonShutdown implements ApplicationListener<ContextClosedEvent> {

	private static final long TIMEOUT = 30 * 1000;

	@Resource
	private TomcatShutdown tomcatShutdown;

	@Autowired(required = false)
	private List<TraceThreadPoolExecutor> threadPoolExecutorList;

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		long begin = System.currentTimeMillis();

		tomcatShutdown.shutdownGracefullyConnector(10, TimeUnit.SECONDS);

		shutdownGracefullyExecutor(begin);
	}

	public void shutdownGracefullyExecutor(long begin) {
		if (CollUtil.isEmpty(threadPoolExecutorList)) {
			return;
		}

		threadPoolExecutorList.forEach(ThreadPoolExecutor::shutdown);

		boolean timeOut = false;
		for (TraceThreadPoolExecutor executor : threadPoolExecutorList) {
			try {
				if (timeOut) {
					shutdownExecutorNow(executor);
					break;
				}

				boolean bool = executor.awaitTermination(TIMEOUT - (System.currentTimeMillis() - begin), TimeUnit.MILLISECONDS);
				if (!bool) {
					// 超时时间外返回，执行强制关闭
					shutdownExecutorNow(executor);
					break;
				}

				// 超时时间内返回，重新计算剩余时间
				if (System.currentTimeMillis() - begin >= TIMEOUT) {
					timeOut = true;
				}
			} catch (InterruptedException e) {
				log.error("【 通用优雅停机 】异常：{}", e.getMessage(), e);

				if (System.currentTimeMillis() - begin >= TIMEOUT) {
					timeOut = true;
				}
			}
		}
	}

	private static void shutdownExecutorNow(TraceThreadPoolExecutor executor) {
		List<Runnable> runnableList = executor.shutdownNow();

		log.info("【 通用优雅停机 】强制关闭，未执行任务数：{}", runnableList.size());
	}

}
