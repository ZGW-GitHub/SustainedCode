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
import com.code.rocketmq.utils.MsgSplitterUtil;
import com.code.rocketmq.utils.ProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Snow
 * @date 2022/11/26 20:01
 */
@Slf4j
public class BatchSendTest extends RocketMqApplicationTest {

	@Test
	void simpleTest() {
		final DefaultMQProducer producer = ProducerUtil.startProducer();

		List<Message> messageList = MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC, 20);

		MsgSplitterUtil splitter = new MsgSplitterUtil(messageList);
		splitter.forEachRemaining(messages -> {
			try {
				// 发送消息
				SendResult sendResult = producer.send(messages);

				System.err.printf("发送结果为：%s \n", sendResult.getSendStatus());
			} catch (Exception e) {
				log.error("发送异常：", e);
			}
		});
	}

}
