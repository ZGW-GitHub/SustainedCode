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

package com.code.java.thread.eee.keyword._volatile;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Snow
 * @date 2020/5/11 9:14 上午
 */
public class VolatileTest {

	private static volatile Integer num = 0;

	private final CountDownLatch latch = new CountDownLatch(1);

	/**
	 * 对任意单个 volatile 变量的读/写具有原子性，但类似于 volatile++ 这种复合操作不具有原子性。
	 */
	@Test
	void test() throws InterruptedException {
		new Thread(() -> {
			try {
				latch.await();

				IntStream.range(1, 1000).forEach(i -> num += 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				latch.await();

				IntStream.range(1, 1000).forEach(i -> num += 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		TimeUnit.SECONDS.sleep(3);

		latch.countDown();

		TimeUnit.SECONDS.sleep(3);

		System.err.println(num);

	}

}
