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

import java.io.RandomAccessFile;
import java.nio.charset.Charset;

/**
 * @author Snow
 * @date 2022/8/3 15:04
 */
@Slf4j
public class ByRandomAccessFileTest {

	/**
	 * 读写文件
	 */
	@Test
	@SneakyThrows
	void test1() {
		try (RandomAccessFile file = new RandomAccessFile(CommonUtil.DEFAULT_FILE_PATH, "rw")) {
			CommonUtil.printFileInfo(file, "操作前");

			file.write("这是个测试：Hello".getBytes(Charset.defaultCharset()));
			CommonUtil.printFileInfo(file, "写入 str 后");

			file.writeInt(12);
			CommonUtil.printFileInfo(file, "写入 int 后");

			file.writeLong(128);
			CommonUtil.printFileInfo(file, "写入 long 后");

			CommonUtil.printString("-------------------------- 下面开始读取 --------------------------");

			// 跳到文件开头，以从文件开头读取数据
			file.seek(0);
			CommonUtil.printFileInfo(file, "调用 seek 后");

			// 读取数据
			int    fileLength = Long.valueOf(file.length()).intValue();
			byte[] bytes      = new byte[fileLength];
			file.read(bytes, 0, fileLength);
			CommonUtil.printFileInfo(file, "读取数据后");

			CommonUtil.printString(new String(bytes, Charset.defaultCharset()));
		}
	}

}
