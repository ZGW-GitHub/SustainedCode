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

package com.code.io.component.netty.channelhandler.out;

import com.code.io.CommonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;

/**
 * @author Snow
 * @date 2022/9/7 17:36
 */
@Slf4j
public class ChannelOutboundHandlerDemo implements ChannelOutboundHandler {

	private final String prefix;

	public ChannelOutboundHandlerDemo(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.bind(localAddress, promise);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.connect(remoteAddress, localAddress, promise);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.disconnect(promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.close(promise);
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.deregister(promise);
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.read();
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.write(msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.flush();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireExceptionCaught(cause);
	}

}
