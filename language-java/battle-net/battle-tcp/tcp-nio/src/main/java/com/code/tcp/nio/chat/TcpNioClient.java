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

import cn.hutool.core.util.StrUtil;
import com.code.tcp.nio.utils.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/1 17:44
 */
@Slf4j
public class TcpNioClient {

	private final SocketChannel socket;

	@SneakyThrows
	public TcpNioClient() {
		// 1. 打开SocketChannel
		socket = SocketChannel.open();

		// 2. 设置非阻塞状态
		socket.configureBlocking(false);

		// 3. 连接服务器
		if (!socket.connect(new InetSocketAddress(CommonUtil.SERVER_HOST, CommonUtil.SERVER_PORT))) {
			// 如果没有连接到服务器，保持请求连接的状态
			while (!socket.finishConnect()) {
				TimeUnit.SECONDS.sleep(1);
			}
		}

		// 接收消息
		new Thread(() -> {
			while (true) {
				try {
					String msg = receiveMsg();
					if (StrUtil.isNotBlank(msg)) {
						System.err.println(msg);
					}

					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@SneakyThrows
	public void sendMsg(String message) {
		ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
		socket.write(buffer);

		if (CommonUtil.CLOSE_MSG.equals(message)) {
			socket.close();
		}
	}

	@SneakyThrows
	public String receiveMsg() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		String msg = null;
		while (socket.read(buffer) > 0) {
			buffer.flip();

			msg = new String(buffer.array(), 0, buffer.remaining());

			buffer.clear();
		}
		return msg;
	}

}
