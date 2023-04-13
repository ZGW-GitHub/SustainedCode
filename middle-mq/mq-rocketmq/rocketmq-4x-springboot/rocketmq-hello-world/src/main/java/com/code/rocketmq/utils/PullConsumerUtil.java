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

package com.code.rocketmq.utils;

import com.code.rocketmq.ConstantPool;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @author Snow
 * @date 2022/11/27 00:22
 */
@Slf4j
public class PullConsumerUtil {

	public static volatile boolean running = true;

	@SneakyThrows
	public static DefaultLitePullConsumer makeTemplateConsumer(String groupName, String topic) {
		DefaultLitePullConsumer consumer = new DefaultLitePullConsumer(groupName);
		consumer.setNamesrvAddr(ConstantPool.namesrvAddr());
		consumer.setMessageModel(MessageModel.CLUSTERING);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		consumer.setPullBatchSize(20);
		consumer.setPollTimeoutMillis(10000);

		if (topic != null) {
			consumer.subscribe(topic, "*");
		}

		return consumer;
	}

	@SneakyThrows
	public static void startConsumer(DefaultLitePullConsumer consumer) {
		consumer.start();

		System.err.println("启动成功...");
	}

}
