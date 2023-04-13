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

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/12/11 17:36
 */
@Slf4j
public class RedisReentrantLock extends RedisLock {

	public RedisReentrantLock(String key, String value) {
		super(key, value);
	}

	/**
	 * TODO ：重入锁需要一直持有 invokerLock
	 */
	@Override
	public boolean tryLock(long leaseTime, TimeUnit timeUnit) {
		Jedis jedis = RedisLockClient.jedisPool.getResource();

		// Lua 代码的作用：首先使用 Redis exists 命令判断当前 lock 这个锁是否存在，
		// 如果锁不存在的话，直接使用 hincrby 创建一个键为 lock 的 Hash 表，并将 Hash 表中的键 requestId 的 value 值初始为 0 再加 1 ，最后再设置过期时间。
		// 如果当前锁存在，则使用 hexists 判断当前 lock 对应的 Hash 表中是否存在 requestId 这个键，如果存在，再次使用 hincrby 加 1，最后再次设置过期时间。
//		String script = "if (redis.call('exists', KEYS[1]) == 0) then redis.call('hincrby', KEYS[1], ARGV[1], 1); redis.call('pexpire', KEYS[1], ARGV[2]); return 1; end ; " +
//				" if (redis.call('hexists', KEYS[1], ARGV[1]) == 1) then redis.call('hincrby', KEYS[1], ARGV[1], 1); redis.call('pexpire', KEYS[1], ARGV[2]); return 1; end ; return 0;";
		String script = """
					if (redis.call('exists', KEYS[1]) == 0) then
						redis.call('hincrby', KEYS[1], ARGV[1], 1);
						if (ARGV[2] != '0') then
							redis.call('pexpire', KEYS[1], ARGV[2]);
						end
						return 1;
					end
					if (redis.call('hexists', KEYS[1], ARGV[1]) == 1) then
						redis.call('hincrby', KEYS[1], ARGV[1], 1);
						if (ARGV[2] != '0') then
							redis.call('pexpire', KEYS[1], ARGV[2]);
						end
						return 1;
					end
					return 0;
				""";

		leaseTime = leaseTime == 0 ? 0 : timeUnit.toMillis(leaseTime);
		Object result = jedis.eval(script, Collections.singletonList(key), Arrays.asList(value, leaseTime + ""));
		jedis.close();

		return "1".equals(result.toString());
	}

	@Override
	public boolean renewalLock() {
		return false;
	}

	@Override
	public void unlock() {
		Jedis jedis = RedisLockClient.jedisPool.getResource();

		String script = "if (redis.call('hexists', KEYS[1], ARGV[1]) == 0) then return -1 end; "
				+ " local count = redis.call('hincrby', KEYS[1], ARGV[1], -1); "
				+ " if (count > 0) then return count else redis.call('del', KEYS[1]) return 0 end; ";

		Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
		if (Integer.parseInt(result.toString()) <= 0) {
			RedisLockUtil.removeInvokerLock(key);
			RedisLockUtil.stopWatchDog(this);
		}
		jedis.close();
	}

}
