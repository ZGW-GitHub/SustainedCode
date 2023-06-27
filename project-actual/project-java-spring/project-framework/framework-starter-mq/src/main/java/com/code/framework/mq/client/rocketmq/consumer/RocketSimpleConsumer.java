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

import com.code.framework.basic.exception.code.BizExceptionCode;
import com.code.framework.mq.client.MqClient;
import com.code.framework.mq.client.rocketmq.RocketMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @author 愆凡
 * @date 2022/6/16 16:46
 */
@Slf4j
public class RocketSimpleConsumer implements MqClient<DefaultMQPushConsumer, RocketMQConfig.RocketMQConsumerConfig> {

	private final RocketMQConfig.RocketMQConsumerConfig consumerConfig;

	private DefaultMQPushConsumer client;

	public RocketSimpleConsumer(RocketMQConfig.RocketMQConsumerConfig consumerConfig) {
		client = new DefaultMQPushConsumer();
		client.setNamesrvAddr(consumerConfig.getNameSrv());
		client.setConsumerGroup(consumerConfig.getGroup());
		client.setMessageModel(MessageModel.valueOf(consumerConfig.getMessageModel()));
		client.setConsumeFromWhere(ConsumeFromWhere.valueOf(consumerConfig.getConsumeFromWhere()));

		this.consumerConfig = consumerConfig;
	}

	public DefaultMQPushConsumer start() {
		try {
			client.subscribe(consumerConfig.getSubscribe(), consumerConfig.getSubExpression());

			client.start();
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ RocketMQ Consumer 启动失败 ！！！", e);
			throw BizExceptionCode.COMMON_ERROR.exception();
		}
		return client;
	}

	@Override
	public final DefaultMQPushConsumer client() {
		return client;
	}

}
