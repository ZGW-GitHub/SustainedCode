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

package com.code.test.jedis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/6/6 11:12
 */
@Slf4j
@SpringBootTest
public class RedisClientJedisApplicationTest {

	@Autowired
	protected JedisPool jedisPool;

	protected Jedis jedis;

	@BeforeEach
	public void before() {
		jedis = jedisPool.getResource();
	}

	@Test
	void contextTest() {
		long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1);
		long nanos   = TimeUnit.SECONDS.toNanos(1);

		System.err.println(endTime);
		System.err.println(nanos);
	}

}
