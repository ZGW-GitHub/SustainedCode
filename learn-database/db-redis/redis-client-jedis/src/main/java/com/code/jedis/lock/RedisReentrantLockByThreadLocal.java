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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/12/11 17:36
 */
@Slf4j
public class RedisReentrantLockByThreadLocal extends RedisSimpleLock {

	/**
	 * 用来实现可重入性<br/>
	 * Map 中 key 存储 redis key ，value 存储锁的重入次数
	 */
	private static final ThreadLocal<Map<String, Integer>> LOCKS = ThreadLocal.withInitial(HashMap::new);

	public RedisReentrantLockByThreadLocal(String key, String value) {
		super(key, value);
	}

	@Override
	public boolean tryLock(long leaseTime, TimeUnit timeUnit) {
		Map<String, Integer> counts = LOCKS.get();
		if (counts.containsKey(key)) {
			Integer count = counts.get(key);
			counts.put(key, count + 1);
			return true;
		}

		boolean locked = super.tryLock(leaseTime, timeUnit);
		if (locked) {
			counts.put(key, 1);
		}

		return locked;
	}

	@Override
	public boolean renewalLock() {
		return super.renewalLock();
	}

	@Override
	public void unlock() {
		Map<String, Integer> counts = LOCKS.get();
		Integer              count  = counts.getOrDefault(key, 0);
		if (count <= 1) {
			super.unlock();
			counts.remove(key);
		}

		counts.put(key, count - 1);
	}

}
