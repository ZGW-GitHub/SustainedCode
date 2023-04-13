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

import cn.hutool.core.util.StrUtil;
import com.code.tcp.bio.utils.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/1 17:58
 */
@Slf4j
public class TcpBioClient {

	private final Socket socket;

	@SneakyThrows
	public TcpBioClient() {
		// 创建发送端的 Socket 对象
		socket = new Socket(CommonUtil.SERVER_HOST, CommonUtil.SERVER_PORT);

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
	public void sendMsg(String msg) {
		OutputStream outputStream = socket.getOutputStream();

		outputStream.write(msg.getBytes());
	}

	@SneakyThrows
	private String receiveMsg() {
		// 获取输入流
		InputStream inputStream = socket.getInputStream();

		// 循环的读取客户端发送的数据
		byte[] bytes = new byte[1024];
		int    read  = inputStream.read(bytes);
		return new String(bytes, 0, read);
	}

}
