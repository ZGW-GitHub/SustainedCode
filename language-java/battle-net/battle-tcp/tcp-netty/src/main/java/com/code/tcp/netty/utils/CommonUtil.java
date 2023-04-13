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

package com.code.tcp.netty.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/1 17:49
 */
@Slf4j
public class CommonUtil {

	public static final String SERVER_HOST = "127.0.0.1";

	public static final Integer SERVER_PORT = 65001;

	public static final String STATUS_RUNNING = "running";

	public static final String STATUS_STOP = "stop";

	public static final String CLOSE_MSG = "close";

	public static void printBufferInfo(ByteBuffer buffer) {
		printBufferInfo(buffer, null);
	}

	@SneakyThrows
	public static void printBufferInfo(ByteBuffer buffer, String msg) {
		if (msg == null) {
			System.err.printf("[ limit : %s ] [ position : %s ]\n\n", buffer.limit(), buffer.position());
		} else {
			System.err.printf("%s :\n[ limit : %s ] [ position : %s ]\n\n", msg, buffer.limit(), buffer.position());
		}

		TimeUnit.MILLISECONDS.sleep(200);
	}

	@SneakyThrows
	public static void print(String msg) {
		System.out.println(msg + "\n");

		TimeUnit.MILLISECONDS.sleep(200);
	}

}
