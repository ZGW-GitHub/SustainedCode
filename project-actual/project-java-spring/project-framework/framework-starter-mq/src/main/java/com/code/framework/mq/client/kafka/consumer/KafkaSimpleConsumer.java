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

package com.code.framework.mq.client.kafka.consumer;

import com.code.framework.mq.client.MqClient;
import com.code.framework.mq.client.kafka.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * @author 愆凡
 * @date 2022/6/16 15:31
 */
@Slf4j
public class KafkaSimpleConsumer<K, V> implements MqClient<KafkaConsumer<K, V>, KafkaConfig.KafkaConsumerConfig> {

	private final KafkaConsumer<K, V> client;

	public KafkaSimpleConsumer(KafkaConfig.KafkaConsumerConfig consumerConfig) {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerConfig.getClientId());
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerConfig.getBootstrapServer());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerConfig.getGroupId());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerConfig.getKeySerializer());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerConfig.getValueSerializer());

		client = new KafkaConsumer<>(properties);

		client.subscribe(consumerConfig.getSubscribe());
	}

	public final KafkaConsumer<K, V> client() {
		return client;
	}

}
