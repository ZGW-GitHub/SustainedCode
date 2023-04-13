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
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Snow
 */
public class SingleThreadPoolTest {

	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	@Test
	void test() throws InterruptedException {
		// 不能输出了，因为获取 SingleThreadExecutor 时被包装了，不能强转为 ThreadPoolExecutor
		//        System.out.println(((ThreadPoolExecutor)executorService).getActiveCount()); // 0

		IntStream.range(0, 100).boxed().forEach(integer -> executorService.execute(() -> {
			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));

			System.out.println(Thread.currentThread().getName() + " is ok !");
		}));
		TimeUnit.SECONDS.sleep(1);

		// 不能输出了，因为获取 SingleThreadExecutor 时被包装了，不能强转为 ThreadPoolExecutor
		//        System.out.println(((ThreadPoolExecutor)executorService).getActiveCount()); // 1

	}

}
