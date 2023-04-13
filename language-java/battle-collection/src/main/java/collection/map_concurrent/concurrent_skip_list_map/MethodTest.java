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

package collection.map_concurrent.concurrent_skip_list_map;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * round : 四舍五入<br/>
 * ceiling : 向上取<br/>
 * floor : 向下取<br/>
 *
 * @author Snow
 */
@Slf4j
class MethodTest {

	final ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

	@BeforeEach
	void before() {
		map.put(1, "java");
		map.put(5, "C++");
		map.put(10, "C");
	}

	/**
	 * 距离给定 index 最近，且比给定 index 大的 entry 、key
	 */
	@Test
	void ceilingTest() {
		int keyIndex = 7;

		Map.Entry<Integer, String> entry = map.ceilingEntry(keyIndex);
		System.err.println("entry key : " + entry.getKey());
		System.err.println("entry value : " + entry.getValue());

		Integer key = map.ceilingKey(keyIndex);
		System.err.println("key : " + key);
	}

	/**
	 * 距离给定 index 最近，且比给定 index 小的 entry 、key
	 */
	@Test
	void floorTest() {
		int keyIndex = 7;

		Map.Entry<Integer, String> entry = map.floorEntry(keyIndex);
		System.err.println("entry key : " + entry.getKey());
		System.err.println("entry value : " + entry.getValue());

		Integer key = map.floorKey(keyIndex);
		System.err.println("key : " + key);
	}

	/**
	 * 合并
	 */
	@Test
	void mergeTest() {
		String valueChange = map.merge(1, "Python", (oldv, v) -> {
			System.out.println(oldv); // java
			System.out.println(v); // Python

			return oldv + " " + v;
		});

		System.out.println(valueChange); // java Python
		System.out.println(map.get(1)); // java Python
	}

	/**
	 * 计算、估算
	 */
	@Test
	void computeTest() {
		String compute = map.compute(5, (k, v) -> {
			System.out.println(k); // 5
			System.out.println(v); // C++

			return "hello";
		});

		System.out.println(compute); // hello
		System.out.println(map.get(5)); // hello
	}

}
