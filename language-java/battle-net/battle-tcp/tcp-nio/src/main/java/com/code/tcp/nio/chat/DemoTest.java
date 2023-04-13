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

package com.code.tcp.nio.chat;

import com.code.tcp.nio.utils.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/23 14:41
 */
@Slf4j
public class DemoTest {

	@Test
	@SneakyThrows
	public void serverTest() {
		TcpNioServer server = new TcpNioServer();

		server.start();
	}

	@Test
	@SneakyThrows
	public void clientTest() {
		TcpNioClient client = new TcpNioClient();

		// 发送消息
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要发送的消息：");
		while (scanner.hasNextLine()) {
			String msg = scanner.nextLine();
			client.sendMsg(msg);

			if (CommonUtil.CLOSE_MSG.equals(msg)) {
				TimeUnit.SECONDS.sleep(3);
				break;
			}

			System.out.println("请输入要发送的消息：");
		}

		System.out.println("---> client close success !");
	}

}
