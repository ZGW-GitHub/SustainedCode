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

package com.code.mq.kafka.msgtype.msgtype_transactional_todo;

import cn.hutool.core.util.StrUtil;
import com.code.mq.kafka.KafkaApplicationTest;
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
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 */
@Slf4j
public class ProducerTest extends KafkaApplicationTest {

	public static final String TARGET_TOPIC = "MSG_TYPE_TRANSACTIONAL";

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServers;

	@Test
	void producerTest() throws ExecutionException, InterruptedException {
		// 构建 Producer 客户端
		KafkaProducer<String, String> producer = makeClient();
		// 1、初始化事务（每个 Producer 只需要调用一次）
		producer.initTransactions();

		// 2、开启事务
		producer.beginTransaction();

		try {
			for (int i = 0; i < 10; i++) {
				// 发送消息
				RecordMetadata sendMetadata = producer.send(makeMsg()).get();
				System.err.println(sendMetadata.toString());
				TimeUnit.MILLISECONDS.sleep(500);
			}

			// 3、提交事务
			producer.commitTransaction();
		} catch (RuntimeException e) {
			// 4、回滚事务
			producer.abortTransaction();
		}

		producer.close();
	}

	private KafkaProducer<String, String> makeClient() {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, TARGET_TOPIC + "-producer");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// 使用事务时 Producer 必须配置该项
		properties.put(ProducerConfig.ACKS_CONFIG, "all");
		// 注意：不同 Producer ? 机器 的 transactional.id 不能一样
		properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, StrUtil.uuid());

		return new KafkaProducer<>(properties);
	}

	private ProducerRecord<String, String> makeMsg() {
		return new ProducerRecord<>(TARGET_TOPIC, null, null, null, "消息内容", null);
	}

}
