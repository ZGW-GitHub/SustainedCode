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

/**
 * @author Snow
 * @date 2022/11/26 17:43
 */
@Slf4j
public class MsgTagTool {

	public static Message makeAppleMsg(String topic) {
		Message message = new Message();
		message.setTopic(topic);
		message.setBody(("苹果").getBytes(StandardCharsets.UTF_8));
		// 设置 tags
		message.setTags("Apple");

		return message;
	}

	public static Message makeHuaWeiMsg(String topic) {
		Message message = new Message();
		message.setTopic(topic);
		message.setBody(("华为").getBytes(StandardCharsets.UTF_8));
		// 设置 tags
		message.setTags("HuaWei");

		return message;
	}

}
