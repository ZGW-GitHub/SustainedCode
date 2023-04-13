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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Snow
 * @date 2022/5/18 11:18
 */
@Slf4j
class DirectByteBufferTest {

	final static List<ByteBuffer> buffers = new ArrayList<>();

	@SneakyThrows
	public static void main(String[] args) {
		CommonUtil.nonHeapInfo();

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 100);
		buffers.add(byteBuffer);

		CommonUtil.nonHeapInfo();

		Thread.currentThread().join();
	}

}
