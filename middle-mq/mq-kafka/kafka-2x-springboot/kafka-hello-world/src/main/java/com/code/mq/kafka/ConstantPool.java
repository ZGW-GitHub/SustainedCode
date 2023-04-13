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

package com.code.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Snow
 * @date 2022/12/6 22:30
 */
@Slf4j
@Component
public class ConstantPool {

	public static final String DEFAULT_TOPIC              = "DEFAULT_TOPIC";
	public static final String DEFAULT_PRODUCER_CLIENT_ID = "DefaultProducer";
	public static final String DEFAULT_CONSUMER_CLIENT_ID = "DefaultConsumer";
	public static final String DEFAULT_GROUP              = "DefaultGroup";

	public static boolean running = true;

	private static String kafkaServers;

	public static String kafkaServers() {
		return kafkaServers;
	}

	@Value("${spring.kafka.bootstrap-servers}")
	public void setNamesrvAddr(String value) {
		kafkaServers = value;
	}

	public static void setRunning(boolean running) {
		ConstantPool.running = running;
	}

}
