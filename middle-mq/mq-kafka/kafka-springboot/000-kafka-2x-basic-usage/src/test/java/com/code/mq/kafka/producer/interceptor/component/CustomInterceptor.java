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

package com.code.mq.kafka.producer.interceptor.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Snow
 * @date 2022/4/3 23:15
 */
@Slf4j
public class CustomInterceptor implements ProducerInterceptor<String, String> {

	private final LongAdder sendSuccess = new LongAdder();
	private final LongAdder sendFailure = new LongAdder();

	/**
	 * Producer 在将消息序列化和计算分区前会调用该方法
	 *
	 * @param record 原始消息
	 *
	 * @return 处理后的消息
	 */
	@Override
	public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
		return record;
	}

	/**
	 * Producer 在消息被应答之前或消息发送失败时会调用该方法。
	 * 注意：该方法优先于用户设定的 Callback 之前执行。该方法运行在 Producer 的 I/O 线程中，所以该方法中的逻辑越简单越好。
	 *
	 * @param metadata  发送消息时该消息的元数据
	 * @param exception 消费消息时引发的异常
	 */
	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		if (exception == null) {
			sendSuccess.increment();
		} else {
			sendFailure.increment();
		}
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
