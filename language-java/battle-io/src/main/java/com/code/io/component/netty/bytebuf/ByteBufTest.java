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

package com.code.io.component.netty.bytebuf;

import com.code.io.CommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2022/8/3 14:19
 */
@Slf4j
class ByteBufTest {

	@Test
	void simple() {
		ByteBuf buffer = Unpooled.buffer(1024 * 1024 * 10);
		CommonUtil.printBufInfo(buffer, "新创建");

		buffer.writeBytes("Hello".getBytes());
		CommonUtil.printBufInfo(buffer, "after write");

	}

	/**
	 * 写入 -> 读一点
	 */
	@Test
	void test1() {
		ByteBuf buffer = Unpooled.buffer(1024 * 1024 * 10);
		CommonUtil.printBufInfo(buffer, "新创建");

		buffer.writeBytes("Hello".getBytes());
		CommonUtil.printBufInfo(buffer, "after write");

		byte[] bytes = new byte[2];
		buffer.readBytes(bytes);
		CommonUtil.printBufInfo(buffer, "after read");
	}

	/**
	 * 写入 -> 一次读完
	 */
	@Test
	void test2() {
		ByteBuf buffer = Unpooled.buffer(1024 * 1024 * 10);
		CommonUtil.printBufInfo(buffer, "新创建");

		buffer.writeBytes("Hello".getBytes());
		CommonUtil.printBufInfo(buffer, "after write");

		byte[] bytes = new byte[buffer.readableBytes()];
		buffer.readBytes(bytes);
		CommonUtil.printBufInfo(buffer, "after read");

		System.out.println(new String(bytes));
	}

	/**
	 * 写入 -> 多次读完
	 */
	@Test
	void test3() {
		ByteBuf buffer = Unpooled.buffer(1024 * 1024 * 10);
		CommonUtil.printBufInfo(buffer, "新创建");

		buffer.writeBytes("Hello".getBytes());
		CommonUtil.printBufInfo(buffer, "after write");

		byte[]        bytes = new byte[2];
		StringBuilder sb    = new StringBuilder();
		while (buffer.isReadable()) { // 还有数据没读
			int readableBytes = buffer.readableBytes();

			buffer.readBytes(bytes, 0, Math.min(readableBytes, bytes.length));
			CommonUtil.printBufInfo(buffer, "after read");

			sb.append(new String(bytes, 0, Math.min(readableBytes, bytes.length)));
		}

		System.out.println(sb);
	}

}
