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

package com.code.java.thread.zzz.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * @author Snow
 * @date 2021/8/18 14:31
 */
@Slf4j
public class CASTest {

	private final AtomicLong num = new AtomicLong(0);

	@Test
	void test() throws InterruptedException {
		IntStream.range(0, 10).boxed().forEach(threadName -> {
			new Thread(() -> {
				for (int i = 0; i < 10; i++) {
					long get = num.incrementAndGet();
					System.err.println(Thread.currentThread().getName() + " -- " + get);
				}
			}, threadName.toString()).start();
		});

		Thread.sleep(2000);
	}

}
