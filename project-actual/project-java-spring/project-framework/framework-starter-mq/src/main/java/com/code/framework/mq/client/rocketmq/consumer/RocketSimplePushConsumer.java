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

package com.code.framework.mq.client.rocketmq.consumer;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.code.framework.basic.exception.code.BizExceptionCode;
import com.code.framework.mq.client.MqClient;
import com.code.framework.mq.client.rocketmq.RocketMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @author 愆凡
 * @date 2022/6/16 16:46
 */
@Slf4j
public class RocketSimplePushConsumer implements MqClient<DefaultMQPushConsumer, RocketMQConfig.RocketMQConsumerConfig> {

	private final RocketMQConfig.RocketMQConsumerConfig consumerConfig;

	private final DefaultMQPushConsumer client;

	public RocketSimplePushConsumer(RocketMQConfig.RocketMQConsumerConfig consumerConfig) {
		try {
			client = new DefaultMQPushConsumer();
			client.setNamesrvAddr(consumerConfig.getNamesrv());
			client.setConsumerGroup(consumerConfig.getGroup());
			client.setMessageModel(MessageModel.valueOf(consumerConfig.getMessageModel()));
			client.setConsumeFromWhere(ConsumeFromWhere.valueOf(consumerConfig.getConsumeFromWhere()));
			client.subscribe(consumerConfig.getSubscribe(), consumerConfig.getSubExpression());

			Object messageListener = ReflectUtil.newInstance(consumerConfig.getMessageListener());
			if (BooleanUtil.isTrue(consumerConfig.getOrderlyConsumption())) {
				client.registerMessageListener((MessageListenerOrderly) messageListener);
			} else {
				client.registerMessageListener((MessageListenerConcurrently) messageListener);
			}

			this.consumerConfig = consumerConfig;
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ Consumer 创建失败, 配置: {}", consumerConfig, e);
			throw BizExceptionCode.MQ_ROCKETMQ_CREATE_FAIL.exception();
		}
	}

	public DefaultMQPushConsumer start() {
		try {
			client.start();
			log.debug("------ RocketMQ ------ Consumer 启动成功, 配置: {}", consumerConfig);
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ Consumer 启动失败, 配置: {}", consumerConfig, e);
			throw BizExceptionCode.MQ_ROCKETMQ_START_FAIL.exception();
		}
		return client;
	}

	@Override
	public final DefaultMQPushConsumer client() {
		return client;
	}

}
