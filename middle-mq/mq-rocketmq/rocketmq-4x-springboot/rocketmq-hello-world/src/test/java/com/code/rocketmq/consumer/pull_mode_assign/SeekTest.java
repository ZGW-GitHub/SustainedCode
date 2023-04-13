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

package com.code.rocketmq.consumer.pull_mode_assign;

import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.utils.PullConsumerUtil;
import com.code.rocketmq.utils.PushConsumerUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/11/27 16:22
 */
@Slf4j
public class SeekTest {

	@Test
	@SneakyThrows
	void assignTest() {
		DefaultLitePullConsumer consumer = PullConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, null);
		PullConsumerUtil.startConsumer(consumer);

		Collection<MessageQueue> mqSet = consumer.fetchMessageQueues(ConstantPool.DEFAULT_TOPIC);
		List<MessageQueue>       list  = new ArrayList<>(mqSet);

		List<MessageQueue> assignList = new ArrayList<>();
		for (int i = 0; i < list.size() / 2; i++) {
			assignList.add(list.get(i));
		}
		consumer.assign(assignList);

		// 设置下次拉取该 MessageQueue 消息的起始位点
		consumer.seek(assignList.get(0), 10);

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
