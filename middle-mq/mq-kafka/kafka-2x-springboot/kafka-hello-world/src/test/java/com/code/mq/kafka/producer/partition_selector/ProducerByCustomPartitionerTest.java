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

package com.code.mq.kafka.producer.partition_selector;

import com.code.mq.kafka.producer.partition_selector.component.CustomPartitioner;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 设置消息 key 并使用自定义的分区器
 *
 * @author Snow
 * @date 2022/4/3 10:57
 */
@Slf4j
public class ProducerByCustomPartitionerTest {

	public static final String TARGET_TOPIC = "PARTITION_SELECTOR";

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServers;

	@Test
	void producerTest() throws ExecutionException, InterruptedException {
		// 构建 Producer 客户端
		KafkaProducer<String, String> producer = makeClient();

		// 发送消息
		for (int i = 0; i < 10; i++) {
			RecordMetadata sendMetadata = producer.send(makeMsg()).get();
			System.err.println(sendMetadata.toString());
		}

		producer.close();
	}

	private KafkaProducer<String, String> makeClient() {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, TARGET_TOPIC + "-producer");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// 设置自定义的分区器
		properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());

		return new KafkaProducer<>(properties);
	}

	private ProducerRecord<String, String> makeMsg() {
		return new ProducerRecord<>(TARGET_TOPIC, null, null, "OrderId", "消息内容", null);
	}

}
