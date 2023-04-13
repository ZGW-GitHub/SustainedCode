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

import cn.hutool.core.util.StrUtil;
import com.code.zookeeper.ZookeeperApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2020/11/9 4:59 下午
 */
@Slf4j
public class ClientTest extends ZookeeperApplicationTest {

	@Test
	void destroy() throws Exception {
		client.delete().guaranteed().deletingChildrenIfNeeded().forPath(NODE_PER_CODE);

		client.close();
	}

	@Test
	void deleteNodeTest() throws Exception {

		client.delete().forPath("/test-1");

		client.delete().guaranteed().forPath("/test-2");

		client.delete().withVersion(10086).forPath("/test-3");

		client.delete().deletingChildrenIfNeeded().forPath("/test-4");

		client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(10086).forPath("/test-5");

	}

	@Test
	void getNodeDataTest() throws Exception {
		client.create().forPath("/test", "initData".getBytes());

		byte[] datas1 = client.getData().forPath("/test");

		log.info("data : " + new String(datas1));


		Stat   stat   = new Stat();
		byte[] datas2 = client.getData().storingStatIn(stat).forPath("/test");

		log.info("data : " + new String(datas2));
		log.info("stat : " + StrUtil.toString(stat));
	}

	@Test
	void updateNodeDataTest() throws Exception {
		client.create().forPath("/test", "initData".getBytes());

		log.info("data : " + new String(client.getData().forPath("/test")));


		Stat stat1 = client.setData().forPath("/test", "updateData".getBytes());

		log.info("data : " + new String(client.getData().forPath("/test")));
		log.info("stat : " + StrUtil.toString(stat1));


		Stat stat2 = client.setData().withVersion(1).forPath("/test", "updateData".getBytes());

		log.info("data : " + new String(client.getData().forPath("/test")));
		log.info("stat : " + StrUtil.toString(stat2));
	}

	@Test
	void checkNodeTest() throws Exception {
		Stat stat1 = client.checkExists().forPath("/test");

		log.info("stat1 : " + (stat1 == null ? "" : StrUtil.toString(stat1)));

		client.create().forPath("/test", "initData".getBytes());

		Stat stat2 = client.checkExists().forPath("/test");

		log.info("stat2 : " + StrUtil.toString(stat2));
	}

	@Test
	void chiledNodeTest() throws Exception {
		client.getChildren().forPath("/").forEach(path -> log.info("path : " + path));

		client.create().forPath("/test");

		client.getChildren().forPath("/").forEach(path -> log.info("path : " + path));
	}

	@Test
	void transactionTest() {

	}

	@Test
	void asyncTest() throws Exception {
		client.create().inBackground((cli, event) -> log.info("eventType : " + event.getType() + " , resultCode : " + event.getResultCode())).forPath("/test");

		Thread.currentThread().join();
	}

}
