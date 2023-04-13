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

package com.code.java.thread.uuu.threadpool.a_task.uu.fork_join_task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * @author Snow
 * @date 2022/3/17 22:46
 */
@Slf4j
public class RecursiveTaskTest {

	/**
	 * 斐波那契数列
	 */
	public static class Fibonacci extends RecursiveTask<Integer> {
		final int n;

		public Fibonacci(int n) {
			this.n = n;
		}

		@Override
		protected Integer compute() {
			if (n <= 1) {
				return n;
			}
			Fibonacci f1 = new Fibonacci(n - 1);
			f1.fork();
			Fibonacci f2 = new Fibonacci(n - 2);
			return f2.compute() + f1.join();
		}
	}

	/**
	 * 异步计算 1+2+3+…+10000 的结果
	 */
	public static class SumTask extends RecursiveTask<Integer> {
		final int start; //开始计算的数
		final int end; //最后计算的数

		public SumTask(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Integer compute() {
			// 如果计算量小于 1000，那么分配一个线程执行 if 中的代码块，并返回执行结果
			if (end - start < 1000) {
				System.out.println(Thread.currentThread().getName() + " 开始执行: " + start + "-" + end);
				int sum = 0;
				for (int i = start; i <= end; i++) {
					sum += i;
				}
				return sum;
			}
			// 如果计算量大于1000，那么拆分为两个任务
			SumTask task1 = new SumTask(start, (start + end) / 2);
			SumTask task2 = new SumTask((start + end) / 2 + 1, end);
			// 执行任务
			task1.fork();
			task2.fork();
			// 获取任务执行的结果
			return task1.join() + task2.join();
		}
	}

}
