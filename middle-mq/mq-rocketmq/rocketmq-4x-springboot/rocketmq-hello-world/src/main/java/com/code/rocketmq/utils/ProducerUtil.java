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

package com.code.rocketmq.utils;

import com.code.rocketmq.ConstantPool;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

/**
 * @author Snow
 * @date 2022/11/26 19:01
 */
@Slf4j
public class ProducerUtil {

	public static DefaultMQProducer startProducer() {
		return startProducer(ConstantPool.DEFAULT_GROUP);
	}

	@SneakyThrows
	public static DefaultMQProducer startProducer(String groupName) {
		DefaultMQProducer producer = new DefaultMQProducer(groupName);
		producer.setNamesrvAddr(ConstantPool.namesrvAddr());
		producer.start();

		return producer;
	}

	@SneakyThrows
	public static TransactionMQProducer startProducer(TransactionListener listener) {
		return startProducer(ConstantPool.DEFAULT_GROUP, listener);
	}

	@SneakyThrows
	public static TransactionMQProducer startProducer(String groupName, TransactionListener listener) {
		TransactionMQProducer producer = new TransactionMQProducer(groupName);
		producer.setNamesrvAddr(ConstantPool.namesrvAddr());
		producer.setTransactionListener(listener);
		producer.start();

		return producer;
	}

}
