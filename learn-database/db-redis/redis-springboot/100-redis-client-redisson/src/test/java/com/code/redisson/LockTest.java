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

package com.code.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/6/6 10:48
 */
@Slf4j
public class LockTest extends RedissonApplicationTest {

	@Autowired
	private RedissonClient redissonClient;

	@Test
	void test1() {
		RLock lock = redissonClient.getLock("test");
		lock.lock(100, TimeUnit.SECONDS);
	}

	@Test
	void readWriteLock() {
		RReadWriteLock lock = redissonClient.getReadWriteLock("test1");
		lock.writeLock().lock();
	}

}
