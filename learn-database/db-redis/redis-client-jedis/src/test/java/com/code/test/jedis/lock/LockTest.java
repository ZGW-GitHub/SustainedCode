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

package com.code.test.jedis.lock;

import com.code.jedis.lock.RedisLock;
import com.code.jedis.lock.RedisLockClient;
import com.code.test.jedis.RedisClientJedisApplicationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Redis 分布式锁
 *
 * @author Snow
 * @date 2021/3/12 09:06
 */
public class LockTest extends RedisClientJedisApplicationTest {

	private final String key   = "orderId_100";
	private final String value = "value_";

	/**
	 * 获取分布式锁（不可重入）
	 */
	@Test
	@SneakyThrows
	public void lockTest() {
		final int            threadNum = 3;
		final CountDownLatch latch     = new CountDownLatch(threadNum);
		IntStream.rangeClosed(1, threadNum).boxed().map(String::valueOf).forEach(i -> {
			new Thread(() -> {
				RedisLock redisLock = RedisLockClient.getLock(key, value + i);

				// 获取锁
				redisLock.lock(20, TimeUnit.SECONDS);

				try {
					TimeUnit.SECONDS.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// 释放锁
					redisLock.unlock();

					latch.countDown();
				}
			}, "Thread_" + i).start();
		});

		latch.await();

		System.err.println("over");
	}

	/**
	 * 获取可重入分布式锁
	 */
	@Test
	void reentrantLockByHashTest() {

	}

}
