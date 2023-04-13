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
import com.code.rocketmq.utils.PushConsumerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.filter.FilterContext;
import org.apache.rocketmq.common.filter.MessageFilter;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2021/12/21 16:44
 */
@Slf4j
public class ByJavaClassTest extends RocketMqApplicationTest {

	@Test
	void consumerTest() {
		final DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, null);

		// 订阅 TODO
		// consumer.subscribe(ConstantPool.DEFAULT_TOPIC);

		PushConsumerUtil.startConsumer(consumer, PushConsumerUtil::concurrently);
	}

	final static class MessageFilterCustom implements MessageFilter {

		@Override
		public boolean match(MessageExt msg, FilterContext context) {
			return msg.getTags().equals("Math") && Integer.parseInt(msg.getProperty("score")) > 0;
		}

	}

}
