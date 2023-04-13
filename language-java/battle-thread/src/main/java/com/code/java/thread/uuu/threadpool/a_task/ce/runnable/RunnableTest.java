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

package com.code.java.thread.uuu.threadpool.a_task.ce.runnable;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author Snow
 * @date 2020/5/11 9:14 上午
 */
@Slf4j
public class RunnableTest {

	@Test
	void threadPoolTest() throws ExecutionException, InterruptedException {
		final ExecutorService executor = Executors.newCachedThreadPool();

		Future<?> future = executor.submit(() -> {
			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(3));

			System.out.println("OK");
		});

		executor.shutdown();

		System.out.println(future.get()); // null
	}

	@Test
	void threadTest() throws InterruptedException {
		new Thread(() -> {
			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(3));

			System.out.println("OK");
		}).start();

		Thread.currentThread().join();
	}

}
