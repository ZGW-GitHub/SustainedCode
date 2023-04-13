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
import com.code.io.component.netty.channelhandler.codec.MessageToMessageCodecDemo;
import com.code.io.component.netty.channelhandler.in.ChannelInboundHandlerDemo;
import com.code.io.component.netty.channelhandler.in.SimpleChannelInboundHandlerDemo;
import com.code.io.component.netty.channelhandler.out.ChannelOutboundHandlerDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2022/8/26 14:26
 */
@Slf4j
class ServerBootstrapTest {

	private final EventLoopGroup parentGroup = new NioEventLoopGroup(1);
	private final EventLoopGroup childGroup  = new NioEventLoopGroup(); // CPU核心数*2

	@Test
	@SneakyThrows
	void test1() {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(parentGroup, childGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.localAddress(CommonUtil.SERVER_HOST, CommonUtil.SERVER_PORT);

		// Min Channel 配置
		serverBootstrap.attr(AttributeKey.newInstance("parent"), "test1");
		serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
		serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
			@Override
			protected void initChannel(NioServerSocketChannel ch) {
				ChannelPipeline pipeline = ch.pipeline();
				// pipeline.addLast(new LoggingHandler(LogLevel.INFO));
				pipeline.addLast(new ChannelInboundHandlerDemo("p"));
				pipeline.addLast(new ChannelOutboundHandlerDemo("p"));
			}
		});

		// Sub Channel 配置
		serverBootstrap.childAttr(AttributeKey.newInstance("child"), "test2");
		serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) {
				ChannelPipeline pipeline = ch.pipeline();
				// pipeline.addLast(new LoggingHandler(LogLevel.INFO));
				pipeline.addLast(MessageToMessageCodecDemo.INSTANCE);
				pipeline.addLast(new ChannelInboundHandlerDemo("c"));
				pipeline.addLast(new SimpleChannelInboundHandlerDemo());
				pipeline.addLast(new ChannelOutboundHandlerDemo("c"));
			}
		});

		ChannelFuture channelFuture = serverBootstrap.bind().sync();

		channelFuture.channel().closeFuture().addListener((ChannelFutureListener) cf -> {
			log.info("Netty Server 关闭");

			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		});

		log.info("Netty Server 启动");
		Thread.currentThread().join();
	}

}
