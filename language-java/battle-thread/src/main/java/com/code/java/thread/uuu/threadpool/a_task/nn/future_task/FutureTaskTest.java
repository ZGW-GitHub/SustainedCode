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

package com.code.java.thread.uuu.threadpool.a_task.nn.future_task;


import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * {@link FutureTask} 实现了 {@link Future} 和 {@link Runnable} 接口，它既可以当做 Runnable 提交到线程池，又能当做 Future 获取自己的执行情况
 *
 * @author Snow
 * @date 2020-03-24 14:04
 */
public class FutureTaskTest {

	private final ExecutorService executor = Executors.newCachedThreadPool();

	@Test
	void callableAdapterTest() throws ExecutionException, InterruptedException {
		FutureTask<String> futureTask = new FutureTask<>(() -> "OK");

		Future<?> future = executor.submit(futureTask);
		executor.shutdown();

		TimeUnit.SECONDS.sleep(2);

		System.out.println(future.get()); // null
		System.out.println(futureTask.get()); // OK
	}

	@Test
	void runnableAdapterTest() throws InterruptedException, ExecutionException {
		FutureTask<String> futureTask =
				new FutureTask<>(() -> System.out.println("-"), "Test");

		Future<?> future = executor.submit(futureTask);
		executor.shutdown();

		TimeUnit.SECONDS.sleep(2);

		System.out.println(future.get()); // null
		System.out.println(futureTask.get()); // Test
	}

}
