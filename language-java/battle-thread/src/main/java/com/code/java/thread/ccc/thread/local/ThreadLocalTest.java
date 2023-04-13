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

package com.code.java.thread.ccc.thread.local;

import cn.hutool.json.JSONUtil;
import com.code.java.thread.ExceptionUtil;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Snow
 * @date 2020/7/10 4:02 下午
 */
@SuppressWarnings("all")
public class ThreadLocalTest {

	@Test
	void simpleTest() throws InterruptedException {
		ThreadLocal<String> threadLocalOne = new ThreadLocal<>();
		ThreadLocal<Integer> threadLocalTwo = new ThreadLocal<>();

		Thread t1 = new Thread(() -> runTask(threadLocalOne, threadLocalTwo, "T1"));
		Thread t2 = new Thread(() -> runTask(threadLocalOne, threadLocalTwo, "T2"));

		t1.start();
		t2.start();

		// TimeUnit.SECONDS.sleep(3);

		System.out.println(threadLocalOne.get());
		System.out.println(threadLocalTwo.get());

		Thread.currentThread().join();
	}

	private void runTask(ThreadLocal<String> threadLocalOne, ThreadLocal<Integer> threadLocalTwo, String tName) {
		ExceptionUtil.processorVoid(() -> {
			threadLocalOne.set(tName + "-1");
			threadLocalOne.set(tName + "-2");
			threadLocalTwo.set(new Random().nextInt());

			TimeUnit.SECONDS.sleep(5);

			System.err.println(tName + "-threadLocalOne : " + threadLocalOne.get());
			System.err.println(tName + "-threadLocalTwo : " + threadLocalTwo.get());
		});
	}

	@Test
	void inheritableThreadLocalTest() {
		// ThreadLocal threadLocal = new ThreadLocal();
		InheritableThreadLocal threadLocal = new InheritableThreadLocal();

		IntStream.range(0, 10).forEach(i -> {
			// 每个线程的序列号，希望在子线程中能够拿到
			threadLocal.set(i);
			// 这里来了一个子线程，我们希望可以访问上面的threadLocal
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
			}).start();

			ExceptionUtil.processorVoid(() -> Thread.sleep(1000));
		});

		System.err.println(JSONUtil.toJsonStr(threadLocal));
	}

}
