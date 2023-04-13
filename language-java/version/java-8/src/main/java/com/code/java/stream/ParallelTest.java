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

package com.code.java.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Snow
 * @date 2021/6/7 14:27
 */
@Slf4j
public class ParallelTest {

	@Test
	void errorTest() {
		for (int j = 0; j < 10; j++) {
			List<Integer> integers = IntStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

			List<String> strings = new ArrayList<>();
			integers.parallelStream().forEach(i -> strings.add(i.toString()));

			System.out.println(strings); // 打印的长度不一致或抛异常
		}
	}

	@Test
	void rightTest() {
		for (int j = 0; j < 10; j++) {
			List<Integer> integers = IntStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

			List<String> strings = integers.parallelStream().map(Object::toString).collect(Collectors.toList());

			System.out.println(strings);
		}
	}

}
