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

package com.code.rocketmq.msgtype;

import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.RocketMqApplicationTest;
import com.code.rocketmq.utils.MsgGenerateUtil;
import com.code.rocketmq.utils.ProducerUtil;
import com.code.rocketmq.utils.PushConsumerUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.utils.MessageUtil;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2021/12/21 16:45
 */
@Slf4j
public class RequestReplyTest extends RocketMqApplicationTest {

	@Test
	@SneakyThrows
	public void simpleTest() {
		final DefaultMQProducer producer = ProducerUtil.startProducer();

		Message msg = MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC);

		// 发送消息
		Message message = producer.request(msg, 10000);

		System.err.printf("Consumer 发来的 reply 消息：%s \n", message);
	}

	@Test
	@SneakyThrows
	public void consumerTest() {
		// 构建 Consumer
		final DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, ConstantPool.DEFAULT_TOPIC);
		// 构建 Producer
		final DefaultMQProducer replyProducer = ProducerUtil.startProducer();

		PushConsumerUtil.startConsumer(consumer, (MessageListenerConcurrently) (msgs, context) -> {
			msgs.forEach(msg -> {
				try {
					String  replyTo      = MessageUtil.getReplyToClient(msg);
					Message replyMessage = MessageUtil.createReplyMessage(msg, "我消费成功了".getBytes());

					SendResult sendResult = replyProducer.send(replyMessage);
					if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
						log.warn("发送结果不为 SEND_OK {}", sendResult);
					}
				} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
					log.error("发生异常：{}", e.getMessage(), e);
					throw new RuntimeException(e);
				}
			});

			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
	}

}
