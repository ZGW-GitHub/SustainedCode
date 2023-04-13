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

package com.code.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Snow
 * @date 2022/11/26 19:02
 */
@Slf4j
@Component
public class ConstantPool {

	public static final String DEFAULT_TOPIC = "DEFAULT_TOPIC";
	public static final String DEFAULT_GROUP = "DefaultGroup";

	private static String namesrvAddr;

	public static String namesrvAddr() {
		return namesrvAddr;
	}

	@Value("${mq.rocketmq.name-server}")
	public void setNamesrvAddr(String value) {
		namesrvAddr = value;
	}

}
