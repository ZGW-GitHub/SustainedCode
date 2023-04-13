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

package com.code.java.thread.hhh.lock.readwritelock;

import com.code.java.thread.ExceptionUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Snow
 * @date 2020/5/7 3:37 下午
 */
@SuppressWarnings("all")
public class SimpleTest {

	private final ReentrantReadWriteLock lock      = new ReentrantReadWriteLock();
	private final Lock                   readLock  = lock.readLock();
	private final Lock                   writeLock = lock.writeLock();

	private final CountDownLatch latch = new CountDownLatch(2);

	/**
	 * 读读 不互斥
	 */
	@Test
	void readLockTest() throws InterruptedException {
		new Thread(() -> work(readLock, latch), "T1").start();
		new Thread(() -> work(readLock, latch), "T2").start();

		latch.await();
	}

	/**
	 * 写写 互斥
	 */
	@Test
	void writeLockTest() throws InterruptedException {
		new Thread(() -> work(writeLock, latch), "T1").start();
		new Thread(() -> work(writeLock, latch), "T2").start();

		latch.await();
	}

	/**
	 * 读写 互斥
	 */
	@Test
	void readWriteLockTest() throws InterruptedException {
		new Thread(() -> work(readLock, latch), "T1").start();
		new Thread(() -> work(writeLock, latch), "T2").start();

		latch.await();
	}

	private void work(Lock lock, CountDownLatch latch) {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " 抢到了锁！");

		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));

		lock.unlock();
		System.out.println(Thread.currentThread().getName() + " 释放了锁！");

		latch.countDown();
	}

}
