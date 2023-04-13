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

package com.code.java.thread.uuu.threadpool.c_executor.forkjoinpool;


import com.code.java.thread.uuu.threadpool.a_task.uu.fork_join_task.RecursiveTaskTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Snow
 * @date 2020/5/21 3:18 下午
 */
public class RecursiveTaskOneTest {

	private final ForkJoinPool forkJoinPool = new ForkJoinPool(4); // 最大并发数4

	/**
	 * 实现斐波那契数列
	 */
	@Test
	void fibonacciTest() {
		RecursiveTaskTest.Fibonacci fibonacci = new RecursiveTaskTest.Fibonacci(20);

		long startTime = System.currentTimeMillis();

		Integer result = forkJoinPool.invoke(fibonacci);

		long endTime = System.currentTimeMillis();

		System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
	}

	/**
	 * 异步计算 1+2+3+…+10000 的结果
	 */
	@Test
	@SneakyThrows
	public void sumTest() {
		ForkJoinTask<Integer> task = new RecursiveTaskTest.SumTask(1, 10000);

		forkJoinPool.submit(task);

		System.out.println(task.get());
	}

}
