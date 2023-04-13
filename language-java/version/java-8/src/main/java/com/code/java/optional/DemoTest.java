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

package com.code.java.optional;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author Snow
 * @date 2020/11/23 4:17 下午
 */
@Slf4j
@SuppressWarnings("all")
public class DemoTest {

	@Test
	void createTest() {
		{
			// 返回一个：包含 test 字符串的 Optional
			Optional<String> o1 = Optional.of("test");
			// 抛异常
			Optional<Object> o2 = Optional.of(null);
		}

		{
			// 返回一个：包含 test 字符串的 Optional
			Optional<String> o3 = Optional.ofNullable("test");
			// 返回一个：空 Optional
			Optional<Object> o4 = Optional.ofNullable(null);
		}
	}

	@Test
	void checkTest() {
		// Optional 不为空返回 true
		boolean boo = Optional.of("test").isPresent();
	}

	@Test
	void getTest() {
		{
			// Optional 不为空则返回，为空则抛异常
			String str1 = Optional.ofNullable("test").get();
		}

		{
			// Optional 不为空则返回test，为空则返回test2
			String str2 = Optional.ofNullable("test").orElse("test2");
		}

		{
			// Optional 不为空则返回test，为空则返回Supplier函数返回的值
			String str3 = Optional.ofNullable("test").orElseGet(() -> "test3");
		}

		{
			// Optional 不为空则返回test，为空则抛出Supplier函数返回的异常
			String str4 = Optional.of("test").orElseThrow(() -> new RuntimeException());
		}
	}

	@Test
	void consumerTest() {
		{
			// Optional 不为空则执行consumer函数，为空则不做处理
			Optional.of("test").ifPresent(str -> System.out.println(str));
		}

		{
			// Optional 不为空则执行 mapping 函数，返回的 Optional 是否包含值取决于 mapping 函数的返回值是否为 null
			Optional<String> test1 = Optional.of("test").map(str -> str.toUpperCase());
		}

		{
			// 与 map 的区别在于：
			// map 会将 mapping 函数的返回值包装为 Optional ，所以我们在编写 map 的 mapping 函数时返回值可以是任意类型
			// flatMap 不会这样，所以我们在编写 flatMap 的 mapping 函数时必须手动将返回值包装为 Optional 类型
			Optional<String> test2 = Optional.of("test").flatMap(str -> Optional.of(str.toUpperCase()));
		}
	}

	@Test
	void filteTest() {
		// 如果 Optional 有值并且满足条件则返回包含该值的 Optional ，否则返回空 Optional
		Optional<String> test = Optional.of("test").filter(str -> str.length() > 2);
	}

}
