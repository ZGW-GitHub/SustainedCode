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

package com.code.io.component.netty.channelhandler.codec;

import com.code.io.CommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Snow
 * @date 2021/5/11 11:40
 */
@Slf4j
@Sharable
public class MessageToMessageCodecDemo extends MessageToMessageCodec<ByteBuf, String> {

	public static final MessageToMessageCodecDemo INSTANCE = new MessageToMessageCodecDemo();

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) {
		log.error(CommonUtil.currentMethodName() + " 执行了...");

		ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
		byteBuf.writeBytes(msg.getBytes());
		out.add(byteBuf);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
		log.error(CommonUtil.currentMethodName() + " 执行了...");

		byte[] bytes = new byte[msg.readableBytes()];
		msg.readBytes(bytes);
		String msgStr = new String(bytes);
		out.add(msgStr);
	}

}
