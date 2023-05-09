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

package com.code.mq.kafka.consumer.consumer_multiple_thread;

import com.code.mq.kafka.KafkaApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2021/1/12 14:10
 */
@Slf4j
public class ConsumerTest extends KafkaApplicationTest {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServers;

	@Test
	void consumerFromEarliestTest() throws InterruptedException {
		// 构建 Consumer 客户端
		KafkaConsumer<String, String> consumer = makeClient();

		// 订阅主题
		consumer.subscribe(Collections.singletonList(ProducerTest.TARGET_TOPIC));

		ThreadPoolTaskExecutor executor = makeFixThreadPool();

		// 循环拉取消息
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));

			for (ConsumerRecord<String, String> record : records) {
				executor.execute(() -> log.info("Thread[{}] : {}", record.toString(), record));
			}

			TimeUnit.MILLISECONDS.sleep(500);
		}
	}

	private KafkaConsumer<String, String> makeClient() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, ProducerTest.TARGET_TOPIC + "-consumer");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "Default-Group");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return new KafkaConsumer<>(properties);
	}

	private ThreadPoolTaskExecutor makeFixThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(2000); // 等待队列长度
		executor.setKeepAliveSeconds(60); // 线程的空闲时间
		executor.setThreadNamePrefix("KafkaMultipleThread-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
		// 线程池关闭的时候等待所有任务都完成
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 线程池关闭时等待任务都完成的时间，如果超过这个时间还有没完成的任务，就不等了，以确保应用能够被关闭，而不是阻塞住
		executor.setAwaitTerminationSeconds(60);

		// 如果不初始化，会出现找不到执行器
		executor.initialize();

		return executor;
	}

}
