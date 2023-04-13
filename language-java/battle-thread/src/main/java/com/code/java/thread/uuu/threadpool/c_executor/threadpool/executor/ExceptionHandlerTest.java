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

package com.code.java.thread.uuu.threadpool.c_executor.threadpool.executor;

import com.code.java.thread.ExceptionUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 定义异常处理器，并为 ExecutorService 设置。
 *
 * @author Snow
 */
@Slf4j
public class ExceptionHandlerTest {

	private final ExecutorService executorService = new ThreadPoolExecutor(
			3, 5, 60, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(10), new MyThreadFactory(),
			(runnable, executor) -> {
				log.warn("线程池已满,任务:" + runnable + ",已被拒绝");
				// 处理被拒绝的任务
			});

	/**
	 * 拦截并处理任务执行过程中的异常
	 *
	 * @throws InterruptedException 中断异常
	 */
	@Test
	void exceptionHandlerTest() throws InterruptedException {
		executorService.submit(() -> {
			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));
		});

		IntStream.range(0, 10).boxed().forEach(integer -> executorService.submit(() -> System.out.println(1 / 0)));

		executorService.shutdown();
		Assertions.assertTrue(executorService.awaitTermination(10, TimeUnit.SECONDS));

		TimeUnit.SECONDS.sleep(5);
	}

	private static class MyThreadFactory implements ThreadFactory {
		private final AtomicInteger threadNum = new AtomicInteger();

		@Override
		public Thread newThread(@NonNull Runnable runnable) {
			Thread thread = new Thread(runnable);
			thread.setName("MyThread-" + threadNum.getAndIncrement());
			// 设置异常处理程序
			thread.setUncaughtExceptionHandler((t, cause) -> log.error("The Thread : " + t.getName() + " 执行失败！", cause));

			return thread;
		}
	}

}
