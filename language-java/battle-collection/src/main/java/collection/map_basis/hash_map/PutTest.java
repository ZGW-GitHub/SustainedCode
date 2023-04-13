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

package collection.map_basis.hash_map;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author Snow
 * @date 2022/6/29 14:46
 */
@Slf4j
class PutTest {

	final HashMap<String, String> map = new HashMap<>();

	@Test
	void putTest() {
		System.err.println(map.put("aaa", "aaa"));
		System.err.println(map.put("aaa", "bbb"));
	}

	@Test
	void putIfAbsentTest() {
		System.err.println(map.putIfAbsent("aaa", "aaa"));
		System.err.println(map.putIfAbsent("aaa", "bbb"));
	}

	@Test
	void computeTest() {
		System.err.println(map.compute("aaa", (k, v) -> "aaa"));
		System.err.println(map.compute("aaa", (k, v) -> "bbb"));
	}

	@Test
	void computeIfAbsentTest() {
		System.err.println(map.computeIfAbsent("aaa", (k) -> "aaa"));
		System.err.println(map.computeIfAbsent("aaa", (k) -> "bbb"));
	}

	@Test
	void computeIfPresentTest() {
		map.put("aaa", "init");
		System.err.println(map.computeIfPresent("aaa", (k, v) -> "aaa"));
		System.err.println(map.computeIfPresent("aaa", (k, v) -> "bbb"));
	}

	@AfterEach
	void after() {
		System.err.println(map);
	}

}
