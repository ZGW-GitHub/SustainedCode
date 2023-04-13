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

package com.code.io.component.netty.channelhandler.in;

import com.code.io.CommonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2022/9/7 17:34
 */
@Slf4j
public class ChannelInboundHandlerDemo implements ChannelInboundHandler {

	private final String prefix;

	public ChannelInboundHandlerDemo(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireChannelRegistered();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireChannelUnregistered();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireChannelInactive();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireChannelRead(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireChannelReadComplete();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireUserEventTriggered(evt);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		log.error("[ {} ] " + CommonUtil.currentMethodName() + " 执行了...", prefix);

		ctx.fireChannelWritabilityChanged();
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
