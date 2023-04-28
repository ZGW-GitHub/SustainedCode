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

package com.code.mq.kafka.utils;

import com.code.mq.kafka.ConstantPool;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/12/6 22:28
 */
@Slf4j
public class ConsumerUtil {

	public static KafkaConsumer<String, String> makeConsumer() {
		return new KafkaConsumer<>(commonProperties());
	}

	public static KafkaConsumer<String, String> makeConsumer(Properties properties) {
		Properties common = commonProperties();
		common.putAll(properties);
		return new KafkaConsumer<>(common);
	}

	public static Properties commonProperties() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConstantPool.kafkaServers());
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, ConstantPool.DEFAULT_CONSUMER_CLIENT_ID);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, ConstantPool.DEFAULT_GROUP);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return properties;
	}

	@SneakyThrows
	public static void simpleConsumer(final KafkaConsumer<String, String> consumer) {
		// 循环拉取消息
		while (ConstantPool.running) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));

			for (ConsumerRecord<String, String> record : records) {
				System.err.println(record);
			}

			TimeUnit.MILLISECONDS.sleep(500);
		}
	}

}
