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

package com.code.framework.mq.client.rocketmq.producer;

import com.code.framework.basic.exception.code.BizExceptionCode;
import com.code.framework.mq.client.MqClient;
import com.code.framework.mq.client.rocketmq.RocketMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author 愆凡
 * @date 2022/6/16 16:46
 */
@Slf4j
public class RocketSimpleProducer implements MqClient<DefaultMQProducer, RocketMQConfig.RocketMQProducerConfig> {

	private final RocketMQConfig.RocketMQProducerConfig producerConfig;

	private final DefaultMQProducer client;

	public RocketSimpleProducer(RocketMQConfig.RocketMQProducerConfig producerConfig) {
		client = new DefaultMQProducer();
		client.setNamesrvAddr(producerConfig.getNameSrv());
		client.setProducerGroup(producerConfig.getGroup());

		this.producerConfig = producerConfig;
	}

	public DefaultMQProducer start() {
		try {
			client.start();
			log.debug("------ RocketMQ ------ Producer 启动成功, config : {}", producerConfig);
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ Producer 启动失败, config : {}", producerConfig, e);
			throw BizExceptionCode.COMMON_ERROR.exception();
		}

		return client;
	}

	@Override
	public final DefaultMQProducer client() {
		return client;
	}

}
