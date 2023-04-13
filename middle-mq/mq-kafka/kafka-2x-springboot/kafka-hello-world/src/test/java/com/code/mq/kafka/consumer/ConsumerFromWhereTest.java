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

package com.code.mq.kafka.consumer;

import com.code.mq.kafka.ConstantPool;
import com.code.mq.kafka.KafkaApplicationTest;
import com.code.mq.kafka.utils.ConsumerUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Properties;

/**
 * @author Snow
 * @date 2022/12/7 16:02
 */
@Slf4j
public class ConsumerFromWhereTest extends KafkaApplicationTest {

	@Test
	@SneakyThrows
	void earliestTest() {
		// 构建 Consumer 客户端
		Properties properties = ConsumerUtil.commonProperties();
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		KafkaConsumer<String, String> consumer = ConsumerUtil.makeConsumer(properties);

		// 订阅主题
		consumer.subscribe(Collections.singletonList(ConstantPool.DEFAULT_TOPIC));

		ConsumerUtil.simpleConsumer(consumer);
	}


	@Test
	void latestTest() {
		// 构建 Consumer 客户端
		Properties properties = ConsumerUtil.commonProperties();
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		KafkaConsumer<String, String> consumer = ConsumerUtil.makeConsumer(properties);

		// 订阅主题
		consumer.subscribe(Collections.singletonList(ConstantPool.DEFAULT_TOPIC));

		ConsumerUtil.simpleConsumer(consumer);
	}

	@Test
	void noneTest() {
		// 构建 Consumer 客户端
		Properties properties = ConsumerUtil.commonProperties();
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "none");
		KafkaConsumer<String, String> consumer = ConsumerUtil.makeConsumer(properties);

		// 订阅主题
		consumer.subscribe(Collections.singletonList(ConstantPool.DEFAULT_TOPIC));

		ConsumerUtil.simpleConsumer(consumer);
	}

}
