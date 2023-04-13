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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Snow
 * @date 2021/9/24 13:53
 */
@Slf4j
public class InvokerAllTest {

	@Test
	void demoTest() {
		ExecutorService executor = Executors.newFixedThreadPool(10);

		Task t1 = new Task(10);
		Task t2 = new Task(20);

		try {
			long currentTimeMillis = System.currentTimeMillis();

			List<Future<Integer>> futureList = executor.invokeAll(Arrays.asList(t1, t2));

			System.err.println(System.currentTimeMillis() - currentTimeMillis); // 20秒
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AllArgsConstructor
	static class Task implements Callable<Integer> {

		private int time;

		@Override
		public Integer call() throws Exception {
			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(time));

			return time;
		}
	}

}
