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


import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Snow
 */
public class AtomicIntegerTest {

	private static final    AtomicInteger VALUE2 = new AtomicInteger();
	private static final    Set<Integer>  SET    = Collections.synchronizedSet(new HashSet<>());
	private static final    Set<Integer>  SET2   = Collections.synchronizedSet(new HashSet<>());
	private volatile static int           value  = 0;

	@Test
	@SuppressWarnings("all")
	public void test() throws InterruptedException {
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1_000; i++) {
				SET.add(value);
				SET2.add(VALUE2.getAndIncrement());
				value = value + 1;
				System.out.println(Thread.currentThread().getName() + " --> " + value);
			}
		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1_000; i++) {
				SET.add(value);
				SET2.add(VALUE2.getAndIncrement());
				value = value + 1;
				System.out.println(Thread.currentThread().getName() + " --> " + value);
			}
		});

		Thread t3 = new Thread(() -> {
			for (int i = 0; i < 1_000; i++) {
				SET.add(value);
				SET2.add(VALUE2.getAndIncrement());
				value = value + 1;
				System.out.println(Thread.currentThread().getName() + " --> " + value);
			}
		});

		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();

		System.out.println(SET.size());
		System.out.println(SET2.size());
	}

}
