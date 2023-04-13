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

package com.code.rocketmq.consumer.push;

import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.utils.PushConsumerUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2022/11/27 14:39
 */
@Slf4j
public class AutoCommitTest {

	@Test
	@SneakyThrows
	void orderConsumerTest() {
		DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, ConstantPool.DEFAULT_TOPIC);

		PushConsumerUtil.startConsumer(consumer, (MessageListenerOrderly) (msgs, orderlyContext) -> {
			msgs.forEach(msg -> {
				PushConsumerUtil.printMsg(msg);

				orderlyContext.setAutoCommit(false);
			});

			return ConsumeOrderlyStatus.SUCCESS;
		});
	}

}
