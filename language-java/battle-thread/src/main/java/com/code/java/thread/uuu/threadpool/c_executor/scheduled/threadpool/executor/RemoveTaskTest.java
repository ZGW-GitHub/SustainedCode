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

package com.code.java.thread.uuu.threadpool.c_executor.scheduled.threadpool.executor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/11/30 22:39
 */
@Slf4j
public class RemoveTaskTest {

	private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

	/**
	 * remove 只能移除未开始的任务
	 */
	@Test
	@SneakyThrows
	@SuppressWarnings("all")
	void removeTest() {
		// 执行中
		ScheduledFuture<?> future1 = executor.scheduleWithFixedDelay(() -> {
			while (true) {
				try {
					System.err.println("test1");
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println("被中断了");
					throw new RuntimeException(e);
				}
			}
		}, 1, 1, TimeUnit.SECONDS);

		// 未开始
		ScheduledFuture<?> future2 = executor.scheduleWithFixedDelay(() -> {
			while (true) {
				try {
					System.err.println("test2");
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println("被中断了");
					throw new RuntimeException(e);
				}
			}
		}, 10, 1, TimeUnit.SECONDS);
		// 执行了一次，第二次未开始
		ScheduledFuture<?> future3 = executor.scheduleWithFixedDelay(() -> {
			for (int i = 0; i < 2; i++) {
				try {
					System.err.println("test3");
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println("被中断了");
					throw new RuntimeException(e);
				}
			}
		}, 1, 10, TimeUnit.SECONDS);

		TimeUnit.SECONDS.sleep(5);

		executor.remove((RunnableScheduledFuture<?>) future1); // 失败
		executor.remove((RunnableScheduledFuture<?>) future2); // 成功
		executor.remove((RunnableScheduledFuture<?>) future3); // 成功
		System.err.println("------------------------ over ------------------------");

		TimeUnit.SECONDS.sleep(200);
	}

	/**
	 * 可以中断正在执行的任务，只需要在捕获到异常后退出任务的执行即可
	 */
	@Test
	@SneakyThrows
	@SuppressWarnings("all")
	void futureCancelTest() {
		ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> {
			while (true) {
				try {
					System.err.println("test");
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println("被中断了");
					break; // 退出
				}
			}
		}, 1, 1, TimeUnit.SECONDS);

		TimeUnit.SECONDS.sleep(5);

		future.cancel(true);
		System.err.println("------------------------ over ------------------------");

		TimeUnit.SECONDS.sleep(200);
	}

}
