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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2022/9/20 14:20
 */
@Slf4j
public class ByteBufAllocatorTest {

	@Test
	void test1() {
		PooledByteBufAllocator allocator = new PooledByteBufAllocator(true);

		ByteBuf buffer = allocator.buffer(0, 1024 * 1024 * 10);
	}

	@Test
	void test2() {
		UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(true, false, PlatformDependent.useDirectBufferNoCleaner());

		ByteBuf buffer = allocator.buffer(0, 1024 * 1024 * 10);
	}

}
