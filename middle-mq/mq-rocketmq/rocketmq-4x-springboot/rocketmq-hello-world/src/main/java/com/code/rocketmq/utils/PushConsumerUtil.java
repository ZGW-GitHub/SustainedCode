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

import cn.hutool.json.JSONUtil;
import com.code.rocketmq.ConstantPool;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Snow
 * @date 2022/11/26 18:16
 */
@Slf4j
public class PushConsumerUtil {

	@SneakyThrows
	public static DefaultMQPushConsumer makeTemplateConsumer(String groupName, String topic) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
		consumer.setNamesrvAddr(ConstantPool.namesrvAddr());
		consumer.setMessageModel(MessageModel.CLUSTERING);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		if (topic != null) {
			consumer.subscribe(topic, "*");
		}

		return consumer;
	}

	@SneakyThrows
	public static void startConsumer(DefaultMQPushConsumer consumer, MessageListenerConcurrently listener) {
		// 注册监听器
		consumer.registerMessageListener(listener);

		// 启动 Consumer
		consumer.start();
		System.err.println("启动成功...");
		Thread.currentThread().join();
	}

	@SneakyThrows
	public static void startConsumer(DefaultMQPushConsumer consumer, MessageListenerOrderly listener) {
		// 注册监听器
		consumer.registerMessageListener(listener);

		// 启动 Consumer
		consumer.start();
		System.err.println("启动成功...");
		Thread.currentThread().join();
	}

	/**
	 * TODO 添加 try cache ，对异常进行处理
	 */
	public static ConsumeConcurrentlyStatus concurrently(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context) {
		msgs.forEach(PushConsumerUtil::printMsg);

		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	/**
	 * TODO 添加 try cache ，对异常进行处理
	 */
	public static ConsumeOrderlyStatus order(final List<MessageExt> msgs, final ConsumeOrderlyContext context) {
		msgs.forEach(PushConsumerUtil::printMsg);

		return ConsumeOrderlyStatus.SUCCESS;
	}

	public static void printMsg(MessageExt msg) {
		String msgStr = new String(msg.getBody(), Charset.defaultCharset());

		System.err.printf("%s 消费了消息：%s \n消息内容：%s \n消息的 tags : %s\n", Thread.currentThread().getName(), JSONUtil.toJsonStr(msg), msgStr, msg.getTags());
	}

}
