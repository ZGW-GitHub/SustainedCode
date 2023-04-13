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

package com.code.spring.event.component.threadpool;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Snow
 * @date 2022/2/28 22:05
 */
@Slf4j
@Component
public class ThreadPoolConfig {

	@Bean
	public ExecutorService threadPoolOne() {
		return Executors.newFixedThreadPool(3, new ThreadFactoryBuilder().setNamePrefix("ThreadPoolOne-").build());
	}

	@Bean
	public ExecutorService threadPoolTwo() {
		return Executors.newFixedThreadPool(3, new ThreadFactoryBuilder().setNamePrefix("ThreadPoolTwo-").build());
	}

	@Bean
	public ThreadPoolTaskExecutor threadPoolThree() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(200); // 等待队列长度
		executor.setKeepAliveSeconds(60); // 线程的空闲时间
		executor.setThreadNamePrefix("ThreadPoolThree-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
		// 线程池关闭的时候等待所有任务都完成
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 线程池关闭时等待任务都完成的时间，如果超过这个时间还有没完成的任务，就不等了，以确保应用能够被关闭，而不是阻塞住
		executor.setAwaitTerminationSeconds(60);

		// 如果不初始化，会出现找不到执行器
		executor.initialize();

		return executor;
	}

}
