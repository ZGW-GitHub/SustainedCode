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

package com.code.zookeeper.test.api;

import com.code.zookeeper.ZookeeperApplicationTest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * <h3>Node Type :</h3>
 * <pre>
 * PERSISTENT 				：持久化
 * PERSISTENT_SEQUENTIAL 			：持久化有序
 * EPHEMERAL 				：临时
 * EPHEMERAL_SEQUENTIAL 			：临时有序
 * PERSISTENT_WITH_TTL 			：TTL
 * PERSISTENT_SEQUENTIAL_WITH_TTL 	：TTL 有序
 * CONTAINER 				：容器
 * </pre>
 *
 * @author Snow
 * @date 2022/12/1 17:39
 */
@Slf4j
public class CreateTest extends ZookeeperApplicationTest {

	/**
	 * 持久化
	 */
	@Test
	@SneakyThrows
	void test1() {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					String path = client.create()
							.creatingParentsIfNeeded()
							.withMode(CreateMode.PERSISTENT)
							.forPath("/test1/test1", "持久化".getBytes());

					System.err.printf("创建结束 for ：%s \n", path);
				} catch (Exception e) {
					System.err.printf("创建失败，异常：%s ，%s \n", e.getMessage(), e.getClass());
//					throw new RuntimeException(e);
				}
			}).start();
		}

		TimeUnit.SECONDS.sleep(5);
	}

	/**
	 * 持久化顺序
	 */
	@Test
	@SneakyThrows
	void test2() {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					String path = client.create()
							.creatingParentsIfNeeded()
							.withMode(CreateMode.PERSISTENT_SEQUENTIAL)
							.forPath("/test2/test", "持久化顺序".getBytes());

					System.err.printf("创建结束 for ：%s \n", path);
				} catch (Exception e) {
					System.err.printf("创建失败，异常：%s ，%s \n", e.getMessage(), e.getClass());
//					throw new RuntimeException(e);
				}
			}).start();
		}

		TimeUnit.SECONDS.sleep(5);
	}

	/**
	 * 临时
	 */
	@Test
	@SneakyThrows
	void test3() {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					String path = client.create()
							.creatingParentsIfNeeded()
							.withMode(CreateMode.EPHEMERAL)
							.forPath("/test3/test1", "临时".getBytes());

					System.err.printf("创建结束 for ：%s \n", path);
				} catch (Exception e) {
					System.err.printf("创建失败，异常：%s ，%s \n", e.getMessage(), e.getClass());
//					throw new RuntimeException(e);
				}
			}).start();
		}

		TimeUnit.SECONDS.sleep(10);
	}

	/**
	 * 临时顺序
	 */
	@Test
	@SneakyThrows
	void test4() {

	}

	/**
	 * TTL
	 */
	@Test
	@SneakyThrows
	void test5() {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					String path = client.create()
							.creatingParentsIfNeeded()
							.withMode(CreateMode.PERSISTENT_WITH_TTL)
							.forPath("/test5/test1", "持久化".getBytes());

					System.err.printf("创建结束 for ：%s \n", path);
				} catch (Exception e) {
					System.err.printf("创建失败，异常：%s ，%s \n", e.getMessage(), e.getClass());
//					throw new RuntimeException(e);
				}
			}).start();
		}

		TimeUnit.SECONDS.sleep(5);
	}

	/**
	 * TTL 有序
	 */
	@Test
	@SneakyThrows
	void test6() {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					String path = client.create()
							.creatingParentsIfNeeded()
							.withMode(CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL)
							.forPath("/test6/test1", "持久化".getBytes());

					System.err.printf("创建结束 for ：%s \n", path);
				} catch (Exception e) {
					System.err.printf("创建失败，异常：%s ，%s \n", e.getMessage(), e.getClass());
//					throw new RuntimeException(e);
				}
			}).start();
		}

		TimeUnit.SECONDS.sleep(5);
	}

	/**
	 * 容器
	 */
	@Test
	@SneakyThrows
	void test7() {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					String path = client.create()
							.creatingParentsIfNeeded()
							.withMode(CreateMode.CONTAINER)
							.forPath("/test7/test1", "持久化".getBytes());

					System.err.printf("创建结束 for ：%s \n", path);
				} catch (Exception e) {
					System.err.printf("创建失败，异常：%s ，%s \n", e.getMessage(), e.getClass());
//					throw new RuntimeException(e);
				}
			}).start();
		}

		TimeUnit.SECONDS.sleep(5);
	}


}
