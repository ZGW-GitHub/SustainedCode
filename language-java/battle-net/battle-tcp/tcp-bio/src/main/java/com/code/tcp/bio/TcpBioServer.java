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
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/1 17:59
 */
@Slf4j
@Data
public class TcpBioServer {

	/**
	 * 线程池，一个线程处理一个客户端
	 */
	private final ExecutorService threadPool = Executors.newCachedThreadPool();

	private final ServerSocket serverSocket;

	private Boolean interrupt = false;

	private String status = CommonUtil.STATUS_STOP;

	@SneakyThrows
	public TcpBioServer() {
		// 1、创建 ServerSocket
		serverSocket = new ServerSocket(CommonUtil.SERVER_PORT);
	}

	@SneakyThrows
	public void start() {
		status = CommonUtil.STATUS_RUNNING;

		while (CommonUtil.STATUS_RUNNING.equals(status)) {
			// 2、监听，等待客户端连接
			final Socket clientSocket = serverSocket.accept();

			writeMsg(clientSocket, "登录成功");

			// 3、连接到一个客户端，就创建一个线程，与之通讯(单独写一个方法)
			threadPool.execute(() -> handler(clientSocket));
		}

		serverSocket.close();
		threadPool.shutdown();

		System.err.println("Server Stop Success !");
	}

	public void stop() {
		status = CommonUtil.STATUS_STOP;
	}

	/**
	 * 处理客户端的连接
	 */
	@SneakyThrows
	private void handler(Socket clientSocket) {
		while (CommonUtil.STATUS_RUNNING.equals(status)) {
			String msg = readMsg(clientSocket);

			System.out.println("收到了 : " + msg);

			if (CommonUtil.CLOSE_MSG.equals(msg)) {
				clientSocket.close();
			}

			TimeUnit.SECONDS.sleep(2);
		}
	}

	@SneakyThrows
	private String readMsg(Socket clientSocket) {
		// 获取输入流
		InputStream inputStream = clientSocket.getInputStream();

		// 读取客户端发送的数据
		byte[] bytes = new byte[1024];
		int    read  = inputStream.read(bytes);
		return new String(bytes, 0, read);
	}

	@SneakyThrows
	private void writeMsg(Socket clientSocket, String msg) {
		OutputStream outputStream = clientSocket.getOutputStream();

		outputStream.write(msg.getBytes());
	}

}
