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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/17 11:52
 */
@Slf4j
class MappedByteBufferTest {

	@Test
	@SneakyThrows
	void test1() {
		CommonUtil.nonHeapInfo();

		try (FileChannel fileChannel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH), StandardOpenOption.READ)) {
			MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

			byte[] bytes = new byte[4];
			mappedByteBuffer.get(bytes, 0, bytes.length);
			CommonUtil.printString(new String(bytes));
		}

		CommonUtil.nonHeapInfo();

		TimeUnit.DAYS.sleep(1);
	}

}
