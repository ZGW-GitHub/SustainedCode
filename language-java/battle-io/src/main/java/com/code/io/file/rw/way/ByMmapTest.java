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

package com.code.io.file.rw.way;

import com.code.io.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/15 14:30
 */
@Slf4j
class ByMmapTest {

	/**
	 * 写文件
	 */
	@Test
	@SneakyThrows
	void test1() {
		// 1、获取 FileChannel
		try (FileChannel channel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH)
				, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))) {

			// 2、调用 map 方法
			MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 10L * 1024 * 1024);

			// 3、调用 load 方法
			mappedByteBuffer.load();
			while (!mappedByteBuffer.isLoaded()) {
				TimeUnit.MILLISECONDS.sleep(200);
				System.err.println("loading ...");
			}

			// 4、写操作
			CommonUtil.printBufferInfo(mappedByteBuffer, "写入前");
			mappedByteBuffer.put("test这是测试".getBytes(Charset.defaultCharset()));
			CommonUtil.printBufferInfo(mappedByteBuffer, "写入后");

			// 5、调用 force 方法
			mappedByteBuffer.force();

			// 6、回收 mappedByteBuffer 内存
			// ((DirectBuffer) mappedByteBuffer).cleaner().clean();
		}
	}

	/**
	 * 读文件
	 */
	@Test
	@SneakyThrows
	void test2() {
		// 1、获取 FileChannel
		try (FileChannel channel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH), StandardOpenOption.READ)) {

			// 2、调用 map 方法
			MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, 10L * 1024 * 1024);

			// 3、调用 load 方法
			mappedByteBuffer.load();
			while (!mappedByteBuffer.isLoaded()) {
				TimeUnit.MILLISECONDS.sleep(200);
				System.err.println("loading ...");
			}

			// 4、读操作
			CommonUtil.printBufferInfo(mappedByteBuffer, "读取前");
			byte[] bytes = new byte[16];
			mappedByteBuffer.get(bytes, 0, bytes.length);
			CommonUtil.printString(new String(bytes, Charset.defaultCharset()));
			CommonUtil.printBufferInfo(mappedByteBuffer, "读取后");

			// 5、调用 force 方法
			// mappedByteBuffer.force();

			// 6、回收 mappedByteBuffer 内存
			// ((DirectBuffer) mappedByteBuffer).cleaner().clean();
		}
	}

	/**
	 * 读写文件
	 */
	@Test
	@SneakyThrows
	void test3() {
		// 1、获取 FileChannel
		try (FileChannel channel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH)
				, EnumSet.of(StandardOpenOption.SYNC, StandardOpenOption.WRITE, StandardOpenOption.READ))) {

			// 2、调用 map 方法
			// MappedByteBuffer mappedByteBuffer = channel.map(ExtendedMapMode.READ_WRITE_SYNC, 0, 10L * 1024 * 1024);
			MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 10L * 1024 * 1024);

			// 3、调用 load 方法
			mappedByteBuffer.load();
			while (!mappedByteBuffer.isLoaded()) {
				TimeUnit.MILLISECONDS.sleep(200);
				System.err.println("loading ...");
			}

			// 4、读写操作
			writeAndRead(mappedByteBuffer);

			// 5、调用 force 方法
			mappedByteBuffer.force();

			// 6、回收 mappedByteBuffer 内存
			// ((DirectBuffer) mappedByteBuffer).cleaner().clean();
		}
	}

	private void writeAndRead(MappedByteBuffer mappedByteBuffer) {
		// 写入数据
		CommonUtil.printBufferInfo(mappedByteBuffer, "写入前");
		mappedByteBuffer.put("test这是测试".getBytes(Charset.defaultCharset()));
		CommonUtil.printBufferInfo(mappedByteBuffer, "写入后");

		// 翻转：切换为读
		mappedByteBuffer.flip();

		// 读取数据
		CommonUtil.printBufferInfo(mappedByteBuffer, "读取前");
		byte[] bytes = new byte[mappedByteBuffer.limit()];
		mappedByteBuffer.get(bytes, 0, mappedByteBuffer.limit());
		CommonUtil.printBufferInfo(mappedByteBuffer, "读取后");

		CommonUtil.printString(new String(bytes, Charset.defaultCharset()));
	}

}
