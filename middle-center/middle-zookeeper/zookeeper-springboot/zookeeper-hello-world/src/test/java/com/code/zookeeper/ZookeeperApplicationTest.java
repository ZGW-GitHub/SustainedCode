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

package com.code.zookeeper;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2020/8/29 周六 23:23
 */
@SpringBootTest
public class ZookeeperApplicationTest {

	public static final String           ZOOKEEPER_ADDERS = "linux.big:12181";
	public static final String           NAME_SPACE       = "apiTest";
	public static final String           NODE_PER_CODE    = "/";
	public static       CuratorFramework client           = null;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void beforeEach() {
		client = CuratorFrameworkFactory.builder()
				.connectString(ZOOKEEPER_ADDERS)
				.namespace(NAME_SPACE)
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();

		client.start();
	}

	@AfterEach
	@SneakyThrows
	void afterEach() {
		client.close();

		TimeUnit.SECONDS.sleep(1);
	}

}
