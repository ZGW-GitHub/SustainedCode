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

package com.code.mq.kafka.producer;

import com.code.mq.kafka.ConstantPool;
import com.code.mq.kafka.KafkaApplicationTest;
import com.code.mq.kafka.utils.MsgGenerateUtil;
import com.code.mq.kafka.utils.ProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Snow
 * @date 2022/12/6 22:46
 */
@Slf4j
public class SendWayTest extends KafkaApplicationTest {

	@Test
	void syncTest() {
		KafkaProducer<String, String> producer = ProducerUtil.makeProducer();

		ProducerRecord<String, String> msg = MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC);

		try {
			RecordMetadata metadata = producer.send(msg).get();
			System.err.println(metadata);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void asyncTest() {
		KafkaProducer<String, String> producer = ProducerUtil.makeProducer();

		ProducerRecord<String, String> msg = MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC);

		Future<RecordMetadata> future = producer.send(msg, (metadata, exception) -> {
			if (exception != null) {
				log.error("发送失败 ：{}", exception.getMessage(), exception);
			}
		});

		try {
			RecordMetadata metadata = future.get(10, TimeUnit.SECONDS);
			System.err.println(metadata);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void oneWay() {
		// 需要配置 Producer 的 acks = 0 ，才可以使用这种发送方式。
		// 实际场景下，基本不会使用 oneway 的方式来发送消息，先忽略。
	}

}
