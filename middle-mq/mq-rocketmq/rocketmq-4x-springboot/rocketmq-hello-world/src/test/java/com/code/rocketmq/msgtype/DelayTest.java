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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2021/12/21 16:45
 */
@Slf4j
public class DelayTest extends RocketMqApplicationTest {

	@Test
	@SneakyThrows
	public void simpleTest() {
		final DefaultMQProducer producer = ProducerUtil.startProducer();

		Message msg = MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC);
		// 设置延迟级别
		msg.setDelayTimeLevel(3);

		SendResult sendResult = producer.send(msg, 1000);
		System.err.printf("发送结果为：%s \n", sendResult.getSendStatus());
	}

}
