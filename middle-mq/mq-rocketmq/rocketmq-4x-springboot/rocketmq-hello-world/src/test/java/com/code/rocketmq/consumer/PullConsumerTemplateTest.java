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

package com.code.rocketmq.consumer;

import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.RocketMqApplicationTest;
import com.code.rocketmq.utils.PullConsumerUtil;
import com.code.rocketmq.utils.PushConsumerUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2021/1/11 15:29
 */
@Slf4j
public class PullConsumerTemplateTest extends RocketMqApplicationTest {

	/**
	 * 订阅模式
	 */
	@Test
	@SneakyThrows
	void subscribeTest() {
		DefaultLitePullConsumer consumer = PullConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, ConstantPool.DEFAULT_TOPIC);

		// 可对 Consumer 进行自定义配置

		PullConsumerUtil.startConsumer(consumer);

		while (PullConsumerUtil.running) {
			List<MessageExt> msgList = consumer.poll();
			if (msgList.isEmpty()) {
				TimeUnit.SECONDS.sleep(1);
			}

			msgList.forEach(PushConsumerUtil::printMsg);

			if (!consumer.isAutoCommit()) {
				MessageExt   msg          = msgList.get(0);
				MessageQueue messageQueue = new MessageQueue(msg.getTopic(), msg.getBrokerName(), msg.getQueueId());
				consumer.commit(Collections.singleton(messageQueue), true);
			}
		}
	}

	/**
	 * 分配模式
	 */
	@Test
	@SneakyThrows
	void assignTest() {
		DefaultLitePullConsumer consumer = PullConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, null);

		// 可对 Consumer 进行自定义配置

		PullConsumerUtil.startConsumer(consumer);

		// 1、拉取 Topic 的 MessageQueue 信息
		Collection<MessageQueue> mqSet = consumer.fetchMessageQueues(ConstantPool.DEFAULT_TOPIC);
		List<MessageQueue>       list  = new ArrayList<>(mqSet);
		// 2、进行 MessageQueue 分配
		Set<MessageQueue> assignMessageQueue = new HashSet<>();
		for (int i = 0; i < list.size() / 2; i++) {
			assignMessageQueue.add(list.get(i));
		}
		consumer.assign(assignMessageQueue);

		while (PullConsumerUtil.running) {
			List<MessageExt> msgList = consumer.poll();
			if (msgList.isEmpty()) {
				TimeUnit.SECONDS.sleep(1);
			}

			msgList.forEach(PushConsumerUtil::printMsg);

			if (!consumer.isAutoCommit()) {
				MessageExt   msg          = msgList.get(0);
				MessageQueue messageQueue = new MessageQueue(msg.getTopic(), msg.getBrokerName(), msg.getQueueId());
				consumer.commit(Collections.singleton(messageQueue), true);
			}
		}
	}

}
