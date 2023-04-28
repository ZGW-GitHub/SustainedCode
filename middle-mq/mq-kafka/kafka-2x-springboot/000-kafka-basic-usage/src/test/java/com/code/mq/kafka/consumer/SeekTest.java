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
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/12/7 16:19
 */
@Slf4j
public class SeekTest extends KafkaApplicationTest {

	@Test
	void timeTest() {
		// 构建 Consumer 客户端
		KafkaConsumer<String, String> consumer = ConsumerUtil.makeConsumer();

		// 订阅主题
		consumer.subscribe(Collections.singletonList(ConstantPool.DEFAULT_TOPIC));

		// 执行 seek
		seekToBegin(consumer); // seek 到开始
//		seekToEnd(consumer); // seek 到结束
//		seekToTime(consumer); // 指定时间

		ConsumerUtil.simpleConsumer(consumer);
	}

	@SneakyThrows
	private void seekToBegin(KafkaConsumer<String, String> consumer) {
		Set<TopicPartition> assignment = new HashSet<>();
		while (assignment.isEmpty()) {
			TimeUnit.SECONDS.sleep(1);
			assignment = consumer.assignment(); // 获取分配到的分区
		}

		// 设置开始消费的位置
		consumer.seekToBeginning(assignment);
	}

	@SneakyThrows
	private void seekToEnd(KafkaConsumer<String, String> consumer) {
		Set<TopicPartition> assignment = new HashSet<>();
		while (assignment.isEmpty()) {
			TimeUnit.SECONDS.sleep(1);
			assignment = consumer.assignment(); // 获取分配到的分区
		}

		// 设置开始消费的位置
		consumer.seekToEnd(assignment);
	}

	@SneakyThrows
	private void seekToTime(final KafkaConsumer<String, String> consumer) {
		Set<TopicPartition> assignment = new HashSet<>();
		while (assignment.isEmpty()) {
			TimeUnit.SECONDS.sleep(1);
			assignment = consumer.assignment(); // 获取分配到的分区
		}

		// 组装要查询的分区
		Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();
		for (TopicPartition tp : assignment) {
			timestampsToSearch.put(tp, System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000); // 两天前
		}

		// 调用查询
		Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(timestampsToSearch);

		// 设置开始消费的位置
		for (TopicPartition tp : assignment) {
			OffsetAndTimestamp offsetAndTimestamp = offsets.get(tp);
			if (offsetAndTimestamp != null) {
				consumer.seek(tp, offsetAndTimestamp.offset());
			}
		}
	}

}
