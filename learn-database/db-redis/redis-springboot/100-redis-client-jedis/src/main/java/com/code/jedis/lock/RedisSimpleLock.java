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

import com.code.jedis.tools.lock.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/12/11 17:31
 */
@Slf4j
public class RedisSimpleLock extends RedisLock {

	public RedisSimpleLock(String key, String value) {
		super(key, value);
	}

	@Override
	public boolean tryLock(long leaseTime, TimeUnit timeUnit) {
		Jedis jedis = RedisLockClient.jedisPool.getResource();

		SetParams params = new SetParams();
		params.nx(); // SET IF NOT EXIST
		if (leaseTime > 0 && timeUnit != null) {
			params.px(timeUnit.toMillis(leaseTime));
		}
		String  result = jedis.set(key, value, params);
		boolean locked = "OK".equals(result);

		jedis.close();

		System.err.println(Thread.currentThread().getName() + " get lock : " + locked);

		return locked;
	}

	@Override
	public boolean renewalLock() {
		return false;
	}

	@Override
	public void unlock() {
		Jedis jedis = RedisLockClient.jedisPool.getResource();

		// Lua 代码的作用：首先获取锁对应的 value 值，检查是否与 requestId 相等，如果相等则删除锁（使用 Lua 来确保这些操作的原子性）
		String  script   = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		boolean unLocked = false;
		while (!unLocked) {
			Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
			unLocked = "1".equals(result.toString());
		}

		RedisLockUtil.removeInvokerLock(key);
		RedisLockUtil.stopWatchDog(this);
		jedis.close();
	}

}
