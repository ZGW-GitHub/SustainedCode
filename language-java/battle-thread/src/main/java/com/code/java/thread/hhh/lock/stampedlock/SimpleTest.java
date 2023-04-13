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

package com.code.java.thread.hhh.lock.stampedlock;

import com.code.java.thread.ExceptionUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author Snow
 * @date 2020/5/19 4:47 下午
 */
@SuppressWarnings("all")
public class SimpleTest {

	private final StampedLock    stampedLock = new StampedLock();
	private final CountDownLatch latch       = new CountDownLatch(2);

	/**
	 * 悲观读：读写测试
	 */
	@Test
	void readTest() throws InterruptedException {
		new Thread(() -> doRead(latch), "T1").start();
		new Thread(() -> doWrite(latch), "T2").start();

		latch.await();
	}

	/**
	 * 乐观读：读写测试
	 */
	@Test
	void optimisticReadTest() throws InterruptedException {
		new Thread(() -> doOptimisticRead(latch), "T1").start();
		new Thread(() -> doWrite(latch), "T2").start();

		latch.await();
	}

	/**
	 * 乐观读、悲观读：读读测试
	 */
	@Test
	void optimisticReadTest2() throws InterruptedException {
		new Thread(() -> doOptimisticRead(latch), "T1").start();
		new Thread(() -> doRead(latch), "T2").start();

		latch.await();
	}

	private void doRead(CountDownLatch latch) {
		// 阻塞地获取悲观读锁
		long readLock = stampedLock.readLock();
		System.out.println(Thread.currentThread().getName() + " 抢到了悲观读锁！");

		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));

		// 释放悲观读锁
		stampedLock.unlockRead(readLock);
		System.out.println(Thread.currentThread().getName() + " 释放了悲观读锁！");

		latch.countDown();
	}

	private void doOptimisticRead(CountDownLatch latch) {
		// 阻塞地获取乐观读锁，乐观读锁不需要释放
		long optimisticRead = stampedLock.tryOptimisticRead();
		System.out.println(Thread.currentThread().getName() + " 抢到了乐观读锁！");

		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));

		// 如果乐观读期间有写入，执行补偿机制，比如：获取悲观读来再次读
		if (!stampedLock.validate(optimisticRead)) {
			doRead(latch);
		} else {
			latch.countDown();
		}
	}

	private void doWrite(CountDownLatch latch) {
		// 阻塞地获取写锁
		long writeLock = stampedLock.writeLock();
		System.out.println(Thread.currentThread().getName() + " 抢到了写锁！");

		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));

		// 释放写锁
		stampedLock.unlockWrite(writeLock);
		System.out.println(Thread.currentThread().getName() + " 释放了写锁！");

		latch.countDown();
	}

}
