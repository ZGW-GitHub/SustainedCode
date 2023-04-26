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
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO 待优化点：https://segmentfault.com/a/1190000037526623
 *
 * @author Snow
 * @date 2022/11/30 18:03
 */
@Slf4j
public class WatchDogLockUtil {

	/**
	 * redis lock 线程组
	 */
	private static final ThreadGroup                  THREAD_GROUP        = new ThreadGroup("RedisSimpleLock");

	/**
	 * 线程池：定时执行看门狗任务
	 */
	private static final ScheduledThreadPoolExecutor  SCHEDULED_EXECUTOR  = new ScheduledThreadPoolExecutor(10, new NamedThreadFactory("RedisSimpleLock-WatchDog-", THREAD_GROUP, true));
	/**
	 * 保存 Future ，以供 unlock 时取出来取消任务
	 */
	private static final Map<String, ScheduledFuture> FUTURE_THREAD_LOCAL = new ConcurrentHashMap<>();

	/**
	 * 获取分布式锁（不可重入）
	 *
	 * @param jedis      Redis 客户端
	 * @param lockKey    redis key
	 * @param requestId  请求标识 ：防止加的锁被别人解锁
	 * @param expireTime 过期时间 ：防止死锁
	 * @return 是否获取成功
	 */
	public static boolean lock(Jedis jedis, String lockKey, String requestId, Long expireTime) {
		long renewalInterval = expireTime / 2;

		// 调用 redis 加锁
		SetParams params = new SetParams();
		params.nx(); // SET IF NOT EXIST
		params.px(expireTime);
		String result = jedis.set(lockKey, requestId, params);

		boolean locked = "OK".equals(result);
		log.info("lock [{}] ：{}", lockKey, locked ? "success" : "fail");
		if (locked) {
			submitWatchDogTask(jedis, lockKey, requestId, expireTime, renewalInterval);
		}
		return locked;
	}

	private static void submitWatchDogTask(Jedis jedis, String lockKey, String requestId, long expireTime, long renewalInterval) {
		ScheduledFuture future = FUTURE_THREAD_LOCAL.get(lockKey);
		if (future != null) {
			log.info("为 [{}] remove 看门狗任务", lockKey);
			FUTURE_THREAD_LOCAL.remove(lockKey);
			future.cancel(false);
		}

		Runnable task = () -> {
			// 看门狗任务：执行自动续期
			long endTime = System.currentTimeMillis() + renewalInterval;
			while (true) {
				if (true)

					try {
						String  script  = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('pexpire', KEYS[1], ARGV[2]) else return 0 end";
						Object  result  = jedis.eval(script, Collections.singletonList(lockKey), Arrays.asList(requestId, expireTime + ""));
						boolean success = "1".equals(result.toString());
						log.info("为 [{}] running 看门狗，结果：{}", lockKey, success ? "success" : "fail");
						if (success || System.currentTimeMillis() >= endTime) {
							break;
						}

						TimeUnit.MILLISECONDS.sleep(200);
					} catch (Exception e) {
						if (e instanceof InterruptedException) {
							log.info("执行看门狗任务遭到中断");
							break;
						}

						log.error("执行看门狗任务执行发生异常：{}", e.getMessage(), e);

						try {
							TimeUnit.MILLISECONDS.sleep(800);
						} catch (InterruptedException ex) {
							log.info("执行看门狗任务遭到中断");
						}
					}
			}
		};
		log.info("为 [{}] add 看门狗任务", lockKey);
//		ScheduledFuture<?> future = SCHEDULED_EXECUTOR.scheduleWithFixedDelay(task, renewalInterval, renewalInterval, TimeUnit.MILLISECONDS);
//		futureMap.put(lockKey, future);
	}

	/**
	 * 释放分布式锁（不可重入）
	 *
	 * @param jedis     Redis 客户端
	 * @param lockKey   redis key
	 * @param requestId 请求标识 ：防止加的锁被别人解锁
	 * @return 是否释放成功
	 */
	public static boolean unLock(Jedis jedis, String lockKey, String requestId) {
		// Lua 代码的作用：首先获取锁对应的 value 值，检查是否与 requestId 相等，如果相等则删除锁（使用 Lua 来确保这些操作的原子性）
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

		boolean unLock = "1".equals(result.toString());
		log.info("unLock [{}] ：{}", lockKey, unLock ? "success" : "fail");

		log.info("为 [{}] remove 看门狗任务", lockKey);
//		Map<String, ScheduledFuture> futureMap = FUTURE_THREAD_LOCAL.get();
//		futureMap.get(lockKey).cancel(true);
//		futureMap.remove(lockKey);
//		if (futureMap.isEmpty()) {
//			FUTURE_THREAD_LOCAL.remove();
//		}

		return unLock;
	}

}
