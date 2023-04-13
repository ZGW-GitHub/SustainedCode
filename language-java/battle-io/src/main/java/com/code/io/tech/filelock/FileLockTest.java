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

package com.code.io.tech.filelock;

import com.code.io.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/8/18 10:12
 */
@Slf4j
class FileLockTest {

	private final long[] regionA = new long[]{0, 100};
	private final long[] regionB = new long[]{100, 100};
	private final long[] regionC = new long[]{50, 100};

	/**
	 * 对区域 A 加锁
	 */
	@Test
	@SneakyThrows
	void test1() {
		try (FileChannel fileChannel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH)
				, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))) {
			CommonUtil.printFileChannelInfo(fileChannel, "操作前");

			FileLock fileLock = fileChannel.lock(regionA[0], regionA[1], false);
			CommonUtil.printFileLockInfo(fileLock);

			TimeUnit.DAYS.sleep(1);
		}
	}

	/**
	 * 对区域 B 加锁
	 */
	@Test
	@SneakyThrows
	void test2() {
		try (FileChannel fileChannel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH)
				, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))) {
			CommonUtil.printFileChannelInfo(fileChannel, "操作前");

			FileLock fileLock = fileChannel.lock(regionB[0], regionB[1], false);
			CommonUtil.printFileLockInfo(fileLock);

			TimeUnit.DAYS.sleep(1);
		}
	}

	/**
	 * 对区域 C 加锁
	 */
	@Test
	@SneakyThrows
	void test3() {
		try (FileChannel fileChannel = FileChannel.open(Path.of(CommonUtil.DEFAULT_FILE_PATH)
				, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))) {
			CommonUtil.printFileChannelInfo(fileChannel, "操作前");

			FileLock fileLock = fileChannel.lock(regionC[0], regionC[1], false);
			CommonUtil.printFileLockInfo(fileLock);

			TimeUnit.DAYS.sleep(1);
		}
	}

}
