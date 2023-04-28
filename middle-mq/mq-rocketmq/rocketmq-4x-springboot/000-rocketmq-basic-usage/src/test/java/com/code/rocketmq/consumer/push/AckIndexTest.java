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
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2022/11/27 13:20
 */
@Slf4j
public class AckIndexTest {

	@Test
	@SneakyThrows
	void concurrentlyConsumerTest() {
		DefaultMQPushConsumer consumer = PushConsumerUtil.makeTemplateConsumer(ConstantPool.DEFAULT_GROUP, ConstantPool.DEFAULT_TOPIC);

		PushConsumerUtil.startConsumer(consumer, (MessageListenerConcurrently) (msgs, concurrentlyContext) -> {
			for (int i = 0; i < msgs.size(); i++) {
				PushConsumerUtil.printMsg(msgs.get(i));

				concurrentlyContext.setAckIndex(msgs.size() - 1);
			}

			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
	}

}
