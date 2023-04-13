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

package com.code.zookeeper.test.lock;

import com.code.zookeeper.ZookeeperApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Snow
 * @date 2020/11/20 10:26 上午
 */
@Slf4j
public class LockTest extends ZookeeperApplicationTest {

	public static final String           ZOOKEEPER_ADDERS = "127.0.0.1:2181";
	public static final String           NAME_SPACE       = "zkLockTest";
	public static final String           NODE_PER_CODE    = "/";
	public static       CuratorFramework client           = null;

	@BeforeEach
	public void init() {
		client = CuratorFrameworkFactory.builder()
										.connectString(ZOOKEEPER_ADDERS)
										.namespace(NAME_SPACE)
										.sessionTimeoutMs(5000)
										.connectionTimeoutMs(5000)
										.retryPolicy(new ExponentialBackoffRetry(1000, 3))
										.build();

		client.start();
	}

	public void tryLock() throws Exception {
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/test-4");
	}

}
