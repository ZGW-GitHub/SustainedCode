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

package com.code.io.zero.copy;

import com.code.io.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 * @author Snow
 * @date 2022/8/18 11:07
 */
@Slf4j
class SendFileTest {

	@Test
	@SneakyThrows
	void test1() {
		try (final FileChannel fileChannel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH)
				, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))) {

			try (final FileChannel newFileChannel = FileChannel.open(Path.of(CommonUtil.getFilePath("tempNew.txt"))
					, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))) {

//				fileChannel.transferTo(fileChannel.position(), fileChannel.size(), newFileChannel);
				newFileChannel.transferFrom(fileChannel, fileChannel.position(), fileChannel.size());
			}
		}
	}

}
