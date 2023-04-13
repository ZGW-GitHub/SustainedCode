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

package com.code.java.thread.uuu.threadpool.e_executors.threadpool.executor;

import com.code.java.thread.ExceptionUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Snow
 */
public class FixedThreadPoolTest {

	private final ExecutorService executorService = Executors.newFixedThreadPool(10);

	@Test
	void test() throws InterruptedException {
		// 0
		System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());

		IntStream.range(0, 100).boxed().forEach(i -> executorService.execute(() -> {
			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(1));

			System.out.println(Thread.currentThread().getName() + " is ok !");
		}));
		TimeUnit.SECONDS.sleep(1);

		// 10
		System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());

	}

}
