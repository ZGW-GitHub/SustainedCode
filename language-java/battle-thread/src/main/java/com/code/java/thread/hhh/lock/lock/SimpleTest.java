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

package com.code.java.thread.hhh.lock.lock;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Snow
 * @date 2020/5/7 3:24 下午
 */
@SuppressWarnings("all")
@Slf4j
public class SimpleTest {

	private final Lock lock = new ReentrantLock();

	@Test
	void test() throws InterruptedException {
		new Thread(this::work, "T1").start();
		TimeUnit.MILLISECONDS.sleep(500);
		new Thread(this::work, "T2").start();
		TimeUnit.MILLISECONDS.sleep(500);
		new Thread(this::work, "T3").start();
		TimeUnit.MILLISECONDS.sleep(500);
		new Thread(this::work, "T4").start();
		TimeUnit.MILLISECONDS.sleep(500);
		new Thread(this::work, "T5").start();
		TimeUnit.MILLISECONDS.sleep(500);
		new Thread(this::work, "T6").start();

		Thread.currentThread().join();
	}

	private void work() {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " 抢到了锁！");

		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(3000));

		lock.unlock();
		System.out.println(Thread.currentThread().getName() + " 释放了锁！");
	}

}
