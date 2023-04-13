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

package com.code.java.thread.nnn.block_wakeup.condition;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition.await() 会释放锁资源，唤醒后需要抢到锁才能往下执行
 *
 * @author Snow
 * @date 2022/12/4 17:36
 */
@Slf4j
public class ReleaseLockTest {

	private final ReentrantLock lock      = new ReentrantLock();
	private final Condition     condition = lock.newCondition();

	@Test
	@SneakyThrows
	void test() {
		new Thread(this::waitWakeup, "T1").start();
		new Thread(this::waitWakeup, "T2").start();

		TimeUnit.SECONDS.sleep(1);
		new Thread(this::doWakeup, "T3").start();

		TimeUnit.SECONDS.sleep(20);
	}

	private void waitWakeup() {
		try {
			lock.lock();
			System.err.printf("[%s] 获取到锁 \n", Thread.currentThread().getName());

			condition.await();
			System.err.printf("[%s] 被唤醒 \n", Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			System.err.printf("[%s] 释放锁 \n", Thread.currentThread().getName());
			lock.unlock();
		}
	}

	private void doWakeup() {
		try {
			lock.lock();
			System.err.printf("[%s] 获取到锁 \n", Thread.currentThread().getName());

			condition.signalAll();
			System.err.printf("[%s] 唤醒了其它线程 \n", Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			System.err.printf("[%s] 释放锁 \n", Thread.currentThread().getName());
			lock.unlock();
		}
	}

}
