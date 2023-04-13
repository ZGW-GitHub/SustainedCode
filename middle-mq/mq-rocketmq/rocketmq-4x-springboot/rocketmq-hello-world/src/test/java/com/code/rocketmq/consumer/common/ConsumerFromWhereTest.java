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

package com.code.rocketmq.consumer.common;

import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.RocketMqApplicationTest;
import com.code.rocketmq.utils.PushConsumerUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2021/12/21 16:45
 */
@Slf4j
public class ConsumerFromWhereTest extends RocketMqApplicationTest {

	/**
	 * 从一天前的消息开始消费
	 */
	@Test
	@SneakyThrows
	public void timestampTest() {
		final DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, ConstantPool.DEFAULT_TOPIC);

		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
		consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis() - (1000 * 60 * 60 * 24)));

		PushConsumerUtil.startConsumer(consumer, PushConsumerUtil::concurrently);
	}

	@Test
	@SneakyThrows
	public void firstTest() {
		final DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, ConstantPool.DEFAULT_TOPIC);

		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		PushConsumerUtil.startConsumer(consumer, PushConsumerUtil::concurrently);
	}

	@Test
	@SneakyThrows
	public void lastTest() {
		final DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, ConstantPool.DEFAULT_TOPIC);

		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

		PushConsumerUtil.startConsumer(consumer, PushConsumerUtil::concurrently);
	}

}
