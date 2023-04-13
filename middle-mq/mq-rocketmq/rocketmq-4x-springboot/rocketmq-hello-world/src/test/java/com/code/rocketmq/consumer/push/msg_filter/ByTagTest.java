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

package com.code.rocketmq.consumer.push.msg_filter;

import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.RocketMqApplicationTest;
import com.code.rocketmq.message.MsgTagTool;
import com.code.rocketmq.utils.ProducerUtil;
import com.code.rocketmq.utils.PushConsumerUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2021/12/21 16:44
 */
@Slf4j
public class ByTagTest extends RocketMqApplicationTest {

	@Test
	void consumerAppleTest() throws MQClientException {
		// 构建 Consumer
		final DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP + "A", null);

		// 订阅
		consumer.subscribe(ConstantPool.DEFAULT_TOPIC, "Apple");

		PushConsumerUtil.startConsumer(consumer, PushConsumerUtil::concurrently);
	}

	@Test
	void consumerHuaWeiTest() throws MQClientException {
		// 构建 Consumer
		final DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP + "B", null);

		// 订阅
		consumer.subscribe(ConstantPool.DEFAULT_TOPIC, "HuaWei");

		PushConsumerUtil.startConsumer(consumer, PushConsumerUtil::concurrently);
	}

	@Test
	void consumerAllTest() throws MQClientException {
		// 构建 Consumer
		final DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP + "C", null);

		// 订阅
		consumer.subscribe(ConstantPool.DEFAULT_TOPIC, "Apple || HuaWei");

		PushConsumerUtil.startConsumer(consumer, PushConsumerUtil::concurrently);
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
