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

package com.code.tcp.bio;

import com.code.tcp.bio.utils.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/18 17:07
 */
@Slf4j
class DemoTest {

	@Test
	void serverTest() {
		TcpBioServer server = new TcpBioServer();

		server.start();
	}

	@Test
	@SneakyThrows
	void clientTest() {
		TcpBioClient client = new TcpBioClient();

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
