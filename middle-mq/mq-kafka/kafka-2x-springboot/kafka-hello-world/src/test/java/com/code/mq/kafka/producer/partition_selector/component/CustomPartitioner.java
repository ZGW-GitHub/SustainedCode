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

package com.code.mq.kafka.producer.partition_selector.component;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author Snow
 * @date 2022/4/3 10:59
 */
@Slf4j
public class CustomPartitioner implements Partitioner {

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		return 0;
	}

	@Override
	public void onNewBatch(String topic, Cluster cluster, int prevPartition) {
		Partitioner.super.onNewBatch(topic, cluster, prevPartition);
	}

	/**
	 * 在关闭分区器时会被调用，可以将关闭资源的逻辑写在此处
	 */
	@Override
	public void close() {

	}

	/**
	 * @param configs 生产者的配置
	 */
	@Override
	public void configure(Map<String, ?> configs) {
		log.debug("分区器，configure 方法入参：{}", ObjectUtil.toString(configs));
	}

}
