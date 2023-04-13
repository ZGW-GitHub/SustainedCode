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

package com.code.java.thread.uuu.threadpool.e_executors.forkjoinpool;

import com.code.java.thread.uuu.threadpool.a_task.uu.fork_join_task.RecursiveTaskTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * 实现斐波那契数列
 *
 * @author Snow
 * @date 2021/4/29 14:43
 */
public class ForkJoinPoolTest {

	private final ForkJoinPool forkJoinPool = (ForkJoinPool) Executors.newWorkStealingPool();

	@Test
	void fibonacciTest() {
		RecursiveTaskTest.Fibonacci fibonacci = new RecursiveTaskTest.Fibonacci(20);

		long startTime = System.currentTimeMillis();

		Integer result = forkJoinPool.invoke(fibonacci);

		long endTime = System.currentTimeMillis();

		System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
	}

}
