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

package com.code.rocketmq.consumer.pull_mode_subscribe;

import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.RocketMqApplicationTest;
import com.code.rocketmq.message.MsgTagTool;
import com.code.rocketmq.utils.ProducerUtil;
import com.code.rocketmq.utils.PullConsumerUtil;
import com.code.rocketmq.utils.PushConsumerUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/11/27 15:54
 */
@Slf4j
public class MsgFilterTest extends RocketMqApplicationTest {

	@Test
	@SneakyThrows
	void subscribeTest() {
		DefaultLitePullConsumer consumer = PullConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, null);

		// 可对 Consumer 进行自定义配置
		consumer.subscribe(ConstantPool.DEFAULT_TOPIC, "HuaWei");

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

	@Test
	@SneakyThrows
	public void producerTest() {
		final DefaultMQProducer producer = ProducerUtil.startProducer(ConstantPool.DEFAULT_GROUP);

		SendResult sendResult = producer.send(MsgTagTool.makeAppleMsg(ConstantPool.DEFAULT_TOPIC), 1000);
		System.err.printf("发送结果为：%s \n", sendResult.getSendStatus());

		sendResult = producer.send(MsgTagTool.makeHuaWeiMsg(ConstantPool.DEFAULT_TOPIC), 1000);
		System.err.printf("发送结果为：%s \n", sendResult.getSendStatus());
	}

}
