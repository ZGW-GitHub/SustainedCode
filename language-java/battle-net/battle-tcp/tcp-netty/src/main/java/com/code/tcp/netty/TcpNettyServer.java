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

package com.code.tcp.netty;

import com.code.tcp.netty.utils.CommonUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/29 15:44
 */
@Slf4j
public class TcpNettyServer {

	@SneakyThrows
	public TcpNettyServer() {
		EventLoopGroup bossGroup   = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // CPU核心数*2

		ServerBootstrap bootstrap = new ServerBootstrap()
				.group(bossGroup, workerGroup)
				.localAddress(new InetSocketAddress(CommonUtil.SERVER_PORT))
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) {
						ChannelPipeline pipeline = ch.pipeline();
						// 加入一个 Netty 提供 IdleStateHandler ，有 4 个参数（参数值为 0 表示不检测）：
						// 第一个表示读空闲时间，指的是在这段时间内如果没有数据读到，就表示连接假死；
						// 第二个是写空闲时间，指的是在这段时间如果没有写数据，就表示连接假死；
						// 第三个参数是读写空闲时间，表示在这段时间内如果没有产生数据读或者写，就表示连接假死。
						pipeline.addLast(new IdleStateHandler(10, 10, 10, TimeUnit.SECONDS)); // 不能共享，因为每个 Channel 维护的有自己上次的读写时间
					}
				});

		ChannelFuture channelFuture = bootstrap.bind().sync();

		// 对关闭通道进行监听
		channelFuture.channel().closeFuture().addListener((ChannelFutureListener) cf -> {
			log.info("Netty Server 关闭");

			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		});

		log.info("Netty Server 启动");
		Thread.currentThread().join();
	}

}
