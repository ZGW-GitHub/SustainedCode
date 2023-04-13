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

package com.code.java.thread.ooo.lock.util.countdownlatch;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * API：
 * <br />{@link CountDownLatch#await() await()} —— 等待 countdown 达标
 * <br />{@link CountDownLatch#countDown() countDown()} —— countdown
 *
 * @author Snow
 * @date 2021/1/5 16:15
 */
@Slf4j
@SuppressWarnings("all")
public class CountDownLatchTest {

	/**
	 * 实现阶段任务
	 */
	@Test
	void baseTest() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		startNewThread(latch, 1, "T1");
		startNewThread(latch, 3, "T2");

		System.out.println("main await");
		latch.await();

		System.out.println("over ");

		Thread.currentThread().join();
	}

	/**
	 * 多线程 await
	 */
	@Test
	void otherAwaitTest() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		startNewThread(latch, 1, "T1");
		startNewThread(latch, 3, "T2");

		Thread t = new Thread(() -> {
			ExceptionUtil.processorVoid(() -> {
				System.out.println(" t 等待中... ");
				System.out.println("Befor await() : count = " + latch.getCount());

				latch.await();

				System.out.println("After await() : count = " + latch.getCount());
				System.out.println(" t 等到了 ");
			});
		});
		t.start();

		System.out.println(" main 等待中... ");
		latch.await();
		System.out.println(" main 等到了 ");

		t.join();
	}

	private void startNewThread(CountDownLatch latch, int sleepSeconds, String threadName) {
		new Thread(() -> {
			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(sleepSeconds));

			System.err.println(Thread.currentThread().getName() + " 阶段 over ");

			System.err.println("Befor countDown() : count = " + latch.getCount());
			latch.countDown();
			System.err.println("After countDown() : count = " + latch.getCount());
		}, threadName).start();
	}

}
