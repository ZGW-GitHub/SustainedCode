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

package com.code.rocketmq.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Snow
 * @date 2022/11/26 17:41
 */
@Slf4j
public class MsgKeysTool {

	public static Message makeMsg(String topic) {
		Message message = new Message();
		message.setTopic(topic);
		message.setBody(("消息").getBytes(StandardCharsets.UTF_8));
		// 设置 keys
		message.setKeys(Arrays.asList("17538144568", "864127700"));

		return message;
	}

}
