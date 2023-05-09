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

package com.code.mq.kafka.consumer.interceptor.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Snow
 * @date 2022/4/3 23:15
 */
@Slf4j
public class CustomInterceptor implements ConsumerInterceptor<String, String> {

	private final LongAdder sendSuccess = new LongAdder();
	private final LongAdder sendFailure = new LongAdder();

	@Override
	public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
		return null;
	}

	@Override
	public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {

	}

	@Override
	public void close() {
		long   failureCount = sendFailure.longValue();
		long   successCount = sendSuccess.longValue();
		double successRatio = (double) successCount / (successCount + failureCount);
		log.info("发送成功率 = {}%", successRatio * 100);
	}

	@Override
	public void configure(Map<String, ?> configs) {

	}

}
