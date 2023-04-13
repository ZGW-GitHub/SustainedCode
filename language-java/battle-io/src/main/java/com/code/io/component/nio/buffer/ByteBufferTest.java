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

package com.code.io.component.nio.buffer;

import com.code.io.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author Snow
 * @date 2022/8/2 14:39
 */
@Slf4j
class ByteBufferTest {

	/**
	 * 写入 -> 读一点
	 */
	@Test
	void test1() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		CommonUtil.printBufferInfo(buffer, "新创建");

		buffer.put("Hello".getBytes());
		CommonUtil.printBufferInfo(buffer, "after write");

		buffer.flip();
		CommonUtil.printBufferInfo(buffer, "调用 flip 后");

		byte[] bytes = new byte[2];
		buffer.get(bytes);
		CommonUtil.printBufferInfo(buffer, "after read");
	}

	/**
	 * 写入 -> 一次读完
	 */
	@Test
	void test2() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		CommonUtil.printBufferInfo(buffer, "新创建");

		buffer.put("Hello".getBytes());
		CommonUtil.printBufferInfo(buffer, "after write");

		buffer.flip();
		CommonUtil.printBufferInfo(buffer, "调用 flip 后");

		int    remaining = buffer.remaining(); // 剩余未读数据量
		byte[] bytes     = new byte[1024];
		buffer.get(bytes, 0, remaining);
		CommonUtil.printBufferInfo(buffer, "after read");

		// System.out.println(new String(buffer.array(), 0, remaining));
		System.out.println(new String(bytes, 0, remaining));
	}

	/**
	 * 写入 -> 多次读完
	 */
	@Test
	void test3() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		CommonUtil.printBufferInfo(buffer, "新创建");

		buffer.put("Hello".getBytes());
		CommonUtil.printBufferInfo(buffer, "after write");

		buffer.flip();
		CommonUtil.printBufferInfo(buffer, "调用 flip 后");

		byte[]        bytes = new byte[2];
		StringBuilder sb    = new StringBuilder();
		while (buffer.hasRemaining()) { // 还有数据没读
			int remaining = buffer.remaining(); // 剩余未读数据量

			buffer.get(bytes, 0, Math.min(remaining, bytes.length));
			CommonUtil.printBufferInfo(buffer, "after read");

			sb.append(new String(bytes, 0, Math.min(remaining, bytes.length)));
		}

		System.out.println(sb);
	}

	/**
	 * 写入 -> 读完 -> 写入
	 */
	@Test
	void test4() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		CommonUtil.printBufferInfo(buffer, "新创建");

		buffer.put("Hello".getBytes());
		CommonUtil.printBufferInfo(buffer, "after write");

		buffer.flip();
		CommonUtil.printBufferInfo(buffer, "调用 flip 后");

		int    remaining = buffer.remaining(); // 剩余未读数据量
		byte[] bytes     = new byte[1024];
		buffer.get(bytes, 0, remaining);
		CommonUtil.printBufferInfo(buffer, "after read");
		System.out.println(new String(bytes, 0, remaining));

		buffer.clear();
		CommonUtil.printBufferInfo(buffer, "after clear");

		buffer.put("Hello".getBytes());
		CommonUtil.printBufferInfo(buffer, "after write");
	}

	/**
	 * 读写基本类型（不使用带 index 的方法）<p/>
	 * 不需要自己处理 position
	 */
	@Test
	void test5() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		CommonUtil.printBufferInfo(buffer, "新创建");

		buffer.putInt(128);
		CommonUtil.printBufferInfo(buffer, "after write");

		buffer.putLong(128);
		CommonUtil.printBufferInfo(buffer, "after write");

		buffer.flip();
		CommonUtil.printBufferInfo(buffer, "调用 flip 后");

		int num1 = buffer.getInt();
		CommonUtil.printBufferInfo(buffer, "after read : " + num1);

		long num2 = buffer.getLong();
		CommonUtil.printBufferInfo(buffer, "after read : " + num2);
	}

	/**
	 * 读写基本类型（使用带 index 的方法）<p/>
	 * 需要自己处理 position
	 */
	@Test
	void test6() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		CommonUtil.printBufferInfo(buffer, "新创建");

		buffer.putInt(0, 128);
		CommonUtil.printBufferInfo(buffer, "after write");
		buffer.position(4);

		buffer.putLong(buffer.position(), 128);
		CommonUtil.printBufferInfo(buffer, "after write");
		buffer.position(buffer.position() + 8);

		buffer.flip();
		CommonUtil.printBufferInfo(buffer, "调用 flip 后");

		int num1 = buffer.getInt(0);
		CommonUtil.printBufferInfo(buffer, "after read : " + num1);
		buffer.position(4);

		long num2 = buffer.getLong(buffer.position());
		CommonUtil.printBufferInfo(buffer, "after read : " + num2);
		buffer.position(buffer.position() + 8);
	}

}
