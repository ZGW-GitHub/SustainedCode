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

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 * @author Snow
 * @date 2022/8/15 14:23
 */
@Slf4j
class ByFileChannelTest {

	/**
	 * 写入文件
	 */
	@Test
	@SneakyThrows
	void test1() {
		try (FileChannel fileChannel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH)
				, EnumSet.of(StandardOpenOption.WRITE))) {
			CommonUtil.printFileChannelInfo(fileChannel, "操作前");

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.put("Hello".getBytes());

			// 要切换为读，因为 fileChannel 要读取 buffer 中的内容
			buffer.flip();

			ByteBuffer slice = buffer.slice(0, buffer.limit());
			fileChannel.position(0); // 可选：因为其初始时就是 0
			fileChannel.write(slice);
			CommonUtil.printFileChannelInfo(fileChannel, "after write");
		}
	}

	/**
	 * 读取文件（一次读完）
	 */
	@Test
	@SneakyThrows
	void test2() {
		try (FileChannel fileChannel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH)
				, StandardOpenOption.READ)) {
			CommonUtil.printFileChannelInfo(fileChannel, "操作前");

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			fileChannel.read(buffer);
			CommonUtil.printFileChannelInfo(fileChannel, "after write to fileChannel");

			// 要切换为读，因为 我们 要读取 buffer 中的内容
			buffer.flip();
			CommonUtil.printString(new String(buffer.array(), 0, buffer.limit()));
		}
	}

}
