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

package com.code.framework.mq.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author Snow
 * @date 2023/6/26 15:44
 */
@Slf4j
@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("framework.mq.rocketmq")
public class RocketMQConfig {

	private Map<String, RocketMQProducerConfig> producer;

	private Map<String, RocketMQConsumerConfig> consumer;

	@Data
	public static class RocketMQProducerConfig {
		private String nameSrv;
		private String group;
		private String topic;
	}

	@Data
	public static class RocketMQConsumerConfig {
		private String nameSrv;
		private String group;

		private String subscribe;
		private String subExpression;
		private String messageModel;

		private String consumeFromWhere;
	}

}
