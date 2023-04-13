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

package com.code.io.component.netty.bootstrap;

import com.code.io.CommonUtil;
import com.code.io.component.netty.channelhandler.out.ChannelOutboundHandlerDemo;
import com.code.io.component.netty.channelhandler.codec.MessageToMessageCodecDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * @author Snow
 * @date 2022/8/26 14:26
 */
@Slf4j
class BootstrapTest {

	private final EventLoopGroup group = new NioEventLoopGroup(1);

	@Test
	@SneakyThrows
	void test1() {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.remoteAddress(CommonUtil.SERVER_HOST, CommonUtil.SERVER_PORT);

		// Channel 配置
		bootstrap.attr(AttributeKey.newInstance("client"), "test");
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) {
				ChannelPipeline pipeline = ch.pipeline();
				// pipeline.addLast(new LoggingHandler(LogLevel.INFO));
				pipeline.addLast(new MessageToMessageCodecDemo());
				pipeline.addLast(new ChannelOutboundHandlerDemo("client"));
			}
		});

		bootstrap.connect().addListener((ChannelFutureListener) future -> {
			if (future.isSuccess()) {
				Thread thread = new Thread(() -> sendMsg(future));
				thread.setDaemon(true);
				thread.start();
			} else {
				System.err.println("连接失败！");
			}
		});

		Thread.currentThread().join();
	}

	private static void sendMsg(ChannelFuture future) {
		System.err.println("连接成功！");

		System.err.println("请输入：");
		Scanner scanner = new Scanner(System.in);
		while (!Thread.interrupted()) {
			String msg = scanner.nextLine();
			if (CommonUtil.MSG_CLOSE.equals(msg)) {
				future.channel().close();
				break;
			} else {
				System.out.println("发送了：" + msg);

				future.channel().writeAndFlush(msg);
			}
		}
	}

}
