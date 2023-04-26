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

package com.code.jedis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * @author Snow
 * @date 2022/12/3 22:18
 */
@Slf4j
@Component
public class RedisLockClient {

	public static       JedisPool jedisPool;
	/**
	 * 看门狗执行续期时，失败重试次数
	 */
	public static final int       renewalRetry     = 3;
	/**
	 * 看门狗执行续期的间隔
	 */
	public static final long      renewalLeaseTime = 1000 * 30;
	/**
	 * 看门狗最大续期时间
	 */
	public static final long      maxLeaseTime     = 1000 * 60 * 10;

	@Autowired
	public void setJedisPool(JedisPool jedisPool) {
		RedisLockClient.jedisPool = jedisPool;
	}

	public static RedisLock getLock(String lockKey, String value) {
		return new RedisSimpleLock(lockKey, value);
	}

	public static RedisLock getReentrantLock(String lockKey, String value) {
		return new RedisReentrantLock(lockKey, value);
	}

}
