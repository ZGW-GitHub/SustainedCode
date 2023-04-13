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

package com.code.jedis.tools.old;

import com.code.jedis.tools.lock.WatchDogLockUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Snow
 * @date 2022/11/30 18:18
 */
@Slf4j
public class BlockLockUtil {

	private static final ConcurrentHashMap<String, WeakReference<InvokerRedisSemaphore>> tryLockSemaphore       = new ConcurrentHashMap<>();
	/**
	 * 保存 InvokerRedisSemaphore ，以便在获取到锁后移除对 InvokerRedisSemaphore 对象的强引用
	 */
	private static final ThreadLocal<Map<String, InvokerRedisSemaphore>>                 SEMAPHORE_THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

	/**
	 * 尝试获取分布式锁
	 *
	 * @param jedis      Redis 客户端
	 * @param lockKey    redis key
	 * @param requestId  请求标识 ：防止加的锁被别人解锁
	 * @param expireTime 过期时间 ：防止死锁
	 * @return 是否获取成功
	 */
	public static boolean tryLock(Jedis jedis, String lockKey, String requestId, long expireTime, long timeout, TimeUnit timeUnit) {
		final long endTime = System.currentTimeMillis() + timeUnit.toMillis(timeout);
		// 是否拥有调用 redis 的权限
		boolean hasSemaphore = false;
		// 用来控制调用 redis 的权限
		Semaphore semaphore = null;


		while (true) {
			while (true) {
				boolean locked = WatchDogLockUtil.lock(jedis, lockKey, requestId, expireTime);
				// 获取锁成功：
				if (locked) {
					log.info("阻塞地获取 [{}] 的锁 ：成功", lockKey);
					if (hasSemaphore) {
						log.info("release semaphore for ：{}", lockKey);
						semaphore.release();
						Map<String, InvokerRedisSemaphore> semaphoreMap = SEMAPHORE_THREAD_LOCAL.get();
						semaphoreMap.remove(lockKey);
						if (semaphoreMap.isEmpty()) {
							SEMAPHORE_THREAD_LOCAL.remove();
						}
					}
					return true;
				}
				// 获取锁失败，有调用 redis 的权限：
				if (hasSemaphore) {
					if (System.currentTimeMillis() > endTime) {
						log.info("阻塞地获取 [{}] 的锁 ：超时失败", lockKey);
						return false;
					}
					log.info("阻塞地获取 [{}] 的锁 ：失败，稍后重试", lockKey);
					LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(2000));
				}
				// 获取锁失败，没有调用 redis 的权限：
				else {
					break;
				}
			}

			semaphore = getInvokerRedisSemaphore(lockKey);

			// 循环获取调用 redis 的权限
			while (true) {
				try {
					hasSemaphore = semaphore.tryAcquire(800, TimeUnit.MILLISECONDS);
					if (hasSemaphore) {
						// 获取到了调用 redis 的权限
						log.info("get semaphore for [{}] ：success", lockKey);
						break;
					} else {
						// 没获取到调用 redis 的权限
						log.info("get semaphore for [{}] ：fail", lockKey);
						if (System.currentTimeMillis() > endTime) {
							log.info("阻塞地获取 [{}] 的锁 ：超时失败", lockKey);
							return false;
						}
					}
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 创建 Semaphore ，Semaphore 用来争夺调用 redis 的权限
	 *
	 * @param lockKey redis key
	 */
	private static Semaphore getInvokerRedisSemaphore(String lockKey) {
		Map<String, InvokerRedisSemaphore> semaphoreMap = SEMAPHORE_THREAD_LOCAL.get();
		if (semaphoreMap.containsKey(lockKey)) {
			return semaphoreMap.get(lockKey);
		}

		InvokerRedisSemaphore semaphore;
		if (tryLockSemaphore.containsKey(lockKey)) {
			WeakReference<InvokerRedisSemaphore> reference = tryLockSemaphore.get(lockKey);
			semaphore = reference.get();
			if (semaphore == null || reference.enqueue()) {
				semaphore = tryLockSemaphore.compute(lockKey, (k, v) -> new WeakReference<>(new InvokerRedisSemaphore(lockKey, 1))).get();
			}
		} else {
			semaphore = tryLockSemaphore.computeIfAbsent(lockKey, (k) -> new WeakReference<>(new InvokerRedisSemaphore(lockKey, 1))).get();
		}

		semaphoreMap.put(lockKey, semaphore);
		return semaphore;
	}

	@Getter
	static class InvokerRedisSemaphore extends Semaphore {

		private final String lockKey;

		public InvokerRedisSemaphore(String lockKey, int permits) {
			super(permits);
			this.lockKey = lockKey;
		}

	}

}
