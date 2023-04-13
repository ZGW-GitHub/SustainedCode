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

import com.code.java.thread.ExceptionUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 */
public class ScheduledThreadPoolExecutorTest {

	private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

	/**
	 * 一次性延迟任务
	 */
	@Test
	void testScheduleWithCallable() throws ExecutionException, InterruptedException {
		ScheduledFuture<?> future = executor.schedule(() -> {
			System.err.println("执行了！");
			return LocalTime.now();
		}, 5, TimeUnit.SECONDS);

		System.err.println("begin : " + LocalTime.now());

		System.err.println("end : " + future.get());
	}

	/**
	 * 周期任务<br />
	 * 若任务工作时间大于循环时间，下一次循环会在任务完成后立即执行<br />
	 * 若任务工作时间小于循环时间，下一次循环会等待 循环时间 - 任务工作时间 后执行
	 */
	@Test
	void testScheduleAtFixedRate() throws InterruptedException {

		executor.scheduleAtFixedRate(() -> {
			System.out.println("time ：" + LocalTime.now());

			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(8));
		}, 3, 5, TimeUnit.SECONDS);

		System.err.println("begin : " + LocalTime.now());

		Thread.currentThread().join();
	}

	/**
	 * 延迟任务（也是周期的）
	 * 无论任务执行多长时间，下一次循环都要在任务完成后再等待 delay 秒，再执行
	 */
	@Test
	void testScheduleWithFixedDelay() throws InterruptedException {

		executor.scheduleWithFixedDelay(() -> {
			System.out.println("time ：" + LocalTime.now());

			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));
		}, 3, 5, TimeUnit.SECONDS);

		System.err.println("begin : " + LocalTime.now());

		Thread.currentThread().join();
	}

}
