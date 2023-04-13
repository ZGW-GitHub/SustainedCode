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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/1 17:44
 */
@Slf4j
public class TcpNioServer {

	private final ServerSocketChannel serverSocketChannel;

	private final Selector selector;

	private String status = CommonUtil.STATUS_STOP;

	@SneakyThrows
	public TcpNioServer() {
		// 1. 启动 ServerSocketChannel 服务器
		serverSocketChannel = ServerSocketChannel.open();

		// 2. 启动选择器
		selector = Selector.open();

		// 3. 端口绑定
		serverSocketChannel.bind(new InetSocketAddress(CommonUtil.SERVER_PORT));

		// 4. 选择 NIO 方式为非阻塞状态
		serverSocketChannel.configureBlocking(false);

		// 5. 注册 ServerSocketChannel ，监听 OP_ACCEPT 事件
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	@SneakyThrows
	public void start() {
		status = CommonUtil.STATUS_RUNNING;

		while (CommonUtil.STATUS_RUNNING.equals(status)) {
			int readyKeys = selector.select(2000);
			if (0 == readyKeys) {
				TimeUnit.SECONDS.sleep(1);
				continue;
			}

			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();

				// 1. 处理 OP_ACCEPT
				if (key.isAcceptable()) {
					SocketChannel socketChannel = serverSocketChannel.accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ);

					writeMsg((SocketChannel) key.channel(), "登录成功");
				}

				// 2. 处理 OP_READ
				if (key.isReadable()) {
					String msg = readMsg((SocketChannel) key.channel());

					System.out.println("收到了 : " + msg);

					if (CommonUtil.CLOSE_MSG.equals(msg)) {
						key.channel().close();
					}
				}

				iterator.remove();
			}
		}

		serverSocketChannel.close();

		System.err.println("Server Stop Success !");
	}

	public void stop() {
		status = CommonUtil.STATUS_STOP;

		System.err.println("Server Stopping ...");
	}

	@SneakyThrows
	public String readMsg(SocketChannel socketChannel) {
		ByteBuffer    buffer = ByteBuffer.allocate(1024);
		StringBuilder sb     = new StringBuilder();
		while (socketChannel.read(buffer) > 0) {
			buffer.flip();
			sb.append(new String(buffer.array(), 0, buffer.remaining(), Charset.defaultCharset()));
			buffer.clear();
		}

		return sb.toString();
	}

	private void writeMsg(SocketChannel socketChannel, String msg) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

		socketChannel.write(buffer);
	}

}
