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

package com.code.jedis.tools.lock;

import cn.hutool.core.thread.NamedThreadFactory;
import com.code.jedis.lock.RedisLock;
import com.code.jedis.lock.RedisLockClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Snow
 * @date 2022/12/3 22:18
 */
@Slf4j
public class RedisLockUtil {

	/**
	 * redis lock 线程组
	 */
	private static final ThreadGroup THREAD_GROUP = new ThreadGroup("RedisLock");

	/**
	 * TODO ：key 的内存泄漏问题
	 */
	private static final ConcurrentHashMap<String, WeakReference<ReentrantLock>> INVOKER_LOCK_MAP          = new ConcurrentHashMap<>();
	private static final ReferenceQueue<Object>                                  referenceQueue            = new ReferenceQueue<>();
	/**
	 * ThreadLocal ：存储 ReentrantLock ，用于获取调用 redis 的权限
	 */
	private static final ThreadLocal<Map<String, ReentrantLock>>                 INVOKER_LOCK_THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);
	/**
	 * 线程池：定时执行看门狗任务
	 */
	private static final ScheduledThreadPoolExecutor                             SCHEDULED_EXECUTOR        = new ScheduledThreadPoolExecutor(10, new NamedThreadFactory("RedisSimpleLock-WatchDog-", THREAD_GROUP, true));
	/**
	 * 提交到看门狗线程池得到的 Future
	 */
	private static final ConcurrentHashMap<RedisLock, ScheduledFuture>           FUTURE_MAP                = new ConcurrentHashMap<>();

	public static boolean blockingLocking(long waitTime, long leaseTime, TimeUnit timeUnit, RedisLock lock) {
		final Long blockingEndTime        = waitTime == 0 ? null : System.currentTimeMillis() + timeUnit.toMillis(waitTime);
		final long getInvokerLockInterval = 1000;
		final long invokerInterval        = 500;

		ReentrantLock invokerLock = getInvokerLock(lock.getKey());

		boolean canInvoker = false;
		boolean locked     = false;
		try {
			while (!canInvoker) {
				canInvoker = invokerLock.tryLock(getInvokerLockInterval, TimeUnit.MILLISECONDS);
				if (canInvoker) {
					while (!locked) {
						locked = lock.tryLock(leaseTime, timeUnit);
						if (!locked) {
							if (blockingEndTime != null && System.currentTimeMillis() + invokerInterval >= blockingEndTime) {
								break; // 超时退出
							} else {
								System.err.println(Thread.currentThread().getName() + "sleep ...");
								TimeUnit.MILLISECONDS.sleep(invokerInterval);
							}
						}
					}
				} else if (blockingEndTime != null && System.currentTimeMillis() >= blockingEndTime) {
					break; // 超时退出
				}
			}

			if (locked) {
				saveInvokerLock(lock.getKey(), invokerLock);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return locked;
	}

	public static void startWatchDog(RedisLock lock) {
		submitTask(lock, System.currentTimeMillis() + RedisLockClient.maxLeaseTime);
	}

	private static void submitTask(RedisLock lock, long endTime) {
		Runnable task = () -> {
			int     renewalRetry = 1;
			boolean renewal      = false;
			while (renewalRetry <= RedisLockClient.renewalRetry) {
				renewal = lock.renewalLock();
				if (renewal) {
					if (System.currentTimeMillis() < endTime) {
						submitTask(lock, endTime);
					}
					break;
				}
				renewalRetry++;
			}

			if (!renewal) {
				log.warn("看门狗续期失败：超过最大重试次数");
			}
		};

		ScheduledFuture<?> scheduledFuture = SCHEDULED_EXECUTOR.schedule(task, RedisLockClient.renewalLeaseTime / 3, TimeUnit.MILLISECONDS);
		FUTURE_MAP.put(lock, scheduledFuture);
	}

	public static void stopWatchDog(RedisLock lock) {
		if (!FUTURE_MAP.containsKey(lock)) {
			return;
		}

		FUTURE_MAP.get(lock).cancel(false);
		FUTURE_MAP.remove(lock);
	}

	public static ReentrantLock getInvokerLock(String lockKey) {
		ReentrantLock reentrantLock;
		if (INVOKER_LOCK_MAP.containsKey(lockKey)) {
			WeakReference<ReentrantLock> reference = INVOKER_LOCK_MAP.get(lockKey);
			reentrantLock = reference.get();
			if (reentrantLock == null || reference.enqueue()) {
				INVOKER_LOCK_MAP.putIfAbsent(lockKey, new WeakReference<>(new ReentrantLock(true), referenceQueue));
				reentrantLock = INVOKER_LOCK_MAP.get(lockKey).get();
			}
		} else {
			INVOKER_LOCK_MAP.putIfAbsent(lockKey, new WeakReference<>(new ReentrantLock(true), referenceQueue));
			reentrantLock = INVOKER_LOCK_MAP.get(lockKey).get();
		}

		return reentrantLock;
	}

	public static void saveInvokerLock(String lockKey, ReentrantLock lock) {
		Map<String, ReentrantLock> lockMap = INVOKER_LOCK_THREAD_LOCAL.get();
		lockMap.putIfAbsent(lockKey, lock);
	}

	public static void removeInvokerLock(String lockKey) {
		Map<String, ReentrantLock> lockMap       = INVOKER_LOCK_THREAD_LOCAL.get();
		ReentrantLock              reentrantLock = lockMap.remove(lockKey);

		if (reentrantLock != null) {
			reentrantLock.unlock();
		}

		if (lockMap.isEmpty()) {
			INVOKER_LOCK_THREAD_LOCAL.remove();
		}
	}

}
