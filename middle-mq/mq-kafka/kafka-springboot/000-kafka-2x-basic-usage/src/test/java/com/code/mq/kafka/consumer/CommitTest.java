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
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/12/7 16:35
 */
@Slf4j
public class CommitTest extends KafkaApplicationTest {

	@Test
	@SneakyThrows
	void commitTest() {
		// 构建 Consumer 客户端
		KafkaConsumer<String, String> consumer = ConsumerUtil.makeConsumer();

		// 订阅主题
		consumer.subscribe(Collections.singletonList(ConstantPool.DEFAULT_TOPIC));

		syncOne(consumer); // 同步 commit 方式一
		// syncTwo(consumer); // 同步 commit 方式二
		// asyncOne(consumer); // 异步 commit 方式一
		// asyncTwo(consumer); // 异步 commit 方式二
	}

	@SneakyThrows
	private void asyncOne(final KafkaConsumer<String, String> consumer) {
		while (ConstantPool.running) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));

			for (ConsumerRecord<String, String> record : records) {
				System.err.println(record);
			}

			// 上报当前批次的偏移量，上报后执行回调方法
//			consumer.commitAsync();
			consumer.commitAsync((offsets, exception) -> {
				if (exception == null) {
					System.err.println("偏移量异步上报成功");
				} else {
					System.err.println("偏移量异步上报失败，异常为：" + exception.getMessage());
				}
			});

			TimeUnit.MILLISECONDS.sleep(500);
		}
	}

	@SneakyThrows
	private void asyncTwo(final KafkaConsumer<String, String> consumer) {
		while (ConstantPool.running) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));

			Set<TopicPartition> partitions = records.partitions();
			for (TopicPartition partition : partitions) {
				List<ConsumerRecord<String, String>> partitionRecords = records.records(partition); // 该分区的消息

				for (ConsumerRecord<String, String> record : partitionRecords) {
					System.err.println(record);
				}

				// 按分区粒度同步上报
				long lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset(); // 获取拉取到的该分区的最后一条消息的 offset
				consumer.commitAsync(Collections.singletonMap(partition, new OffsetAndMetadata(lastConsumedOffset + 1)), (offsets, exception) -> {
					if (exception == null) {
						System.err.println("偏移量异步上报成功");
					} else {
						System.err.println("偏移量异步上报失败，异常为：" + exception.getMessage());
					}
				});
			}

			TimeUnit.MILLISECONDS.sleep(500);
		}
	}

	@SneakyThrows
	private void syncOne(final KafkaConsumer<String, String> consumer) {
		while (ConstantPool.running) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));

			for (ConsumerRecord<String, String> record : records) {
				System.err.println(record.toString());
			}

			// 上报当前批次的偏移量
			consumer.commitSync(Duration.ofSeconds(3));

			TimeUnit.MILLISECONDS.sleep(500);
		}
	}

	@SneakyThrows
	private void syncTwo(final KafkaConsumer<String, String> consumer) {
		while (ConstantPool.running) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));

			Set<TopicPartition> partitions = records.partitions();
			for (TopicPartition partition : partitions) {
				List<ConsumerRecord<String, String>> partitionRecords = records.records(partition); // 该分区的消息

				for (ConsumerRecord<String, String> record : partitionRecords) {
					System.err.println(record);
				}

				// 按分区粒度同步上报
				long lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset(); // 获取拉取到的该分区的最后一条消息的 offset
				consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastConsumedOffset + 1)));
			}

			TimeUnit.MILLISECONDS.sleep(500);
		}
	}

}
