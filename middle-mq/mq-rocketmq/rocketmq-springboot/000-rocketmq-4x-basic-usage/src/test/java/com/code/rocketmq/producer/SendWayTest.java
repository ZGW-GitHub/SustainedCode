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

package com.code.rocketmq.producer;

import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.RocketMqApplicationTest;
import com.code.rocketmq.utils.MsgGenerateUtil;
import com.code.rocketmq.utils.ProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2022/11/26 19:59
 */
@Slf4j
public class SendWayTest extends RocketMqApplicationTest {

	@Test
	void syncTest() {
		try {
			final DefaultMQProducer producer = ProducerUtil.startProducer();

			// 发送消息
			Message    msg        = MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC);
			SendResult sendResult = producer.send(msg, 1000);

			System.err.printf("发送结果为：%s \n", sendResult.getSendStatus());

		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			log.error("发送发生异常（可能为响应超时等原因）");
			throw new RuntimeException(e);
		}
	}

	@Test
	void asyncTest() {
		final DefaultMQProducer producer = ProducerUtil.startProducer();

		Message msg = MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC);

		try {
			// 发送消息
			producer.send(msg, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {
					System.err.printf("发送结果为：%s \n", sendResult.getSendStatus());
				}

				@Override
				public void onException(Throwable e) {
					System.err.printf("发生异常：%s \n", e);
				}
			}, 3000);
		} catch (MQClientException | RemotingException | InterruptedException e) {
			System.err.println("Error : " + e.getMessage());

			// TODO 发送消息出现异常，重新发送
		}
	}

	@Test
	void onewayTest() {
		final DefaultMQProducer producer = ProducerUtil.startProducer();

		Message msg = MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC);

		try {
			producer.sendOneway(msg);
		} catch (MQClientException | RemotingException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
