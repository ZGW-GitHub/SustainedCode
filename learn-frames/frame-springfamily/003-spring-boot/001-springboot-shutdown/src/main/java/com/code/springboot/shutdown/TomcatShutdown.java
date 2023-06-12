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

package com.code.springboot.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2023/6/12 20:17
 */
@Slf4j
@Component
public class TomcatShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

	private static final int TIMEOUT = 30; // 超时时间，单位：秒

	private Connector connector;

	@Override
	public void customize(Connector connector) {
		this.connector = connector;
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		shutdownGracefullyConnector();
	}

	public void shutdownGracefullyConnector() {
		if (connector == null) {
			return;
		}

		connector.pause();

		Executor executor = connector.getProtocolHandler().getExecutor();
		if (executor instanceof ThreadPoolExecutor threadPoolExecutor) {
			try {
				threadPoolExecutor.shutdown();

				if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
					log.info("Tomcat Protocol Executor 在 {} 秒内无法停止，执行强制关闭。", TIMEOUT);

					List<Runnable> runnableList = threadPoolExecutor.shutdownNow();

					log.info("Tomcat Protocol Executor 强制关闭，未执行任务数：{}", runnableList.size());
				}
			} catch (InterruptedException e) {
				log.error("TomcatShutdown 执行发生异常：{}", e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
		}
	}

}
