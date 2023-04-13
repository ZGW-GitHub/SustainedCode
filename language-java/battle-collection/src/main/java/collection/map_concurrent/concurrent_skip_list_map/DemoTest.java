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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Snow
 * @date 2022/6/29 14:46
 */
@Slf4j
class DemoTest {

	final ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

	@Test
	void test() {
		map.put(5, "2");
		map.put(3, "3");

		System.err.println(map.firstEntry());
	}

	@AfterEach
	void after() {
		System.err.println();
		map.forEach((k, v) -> {
			System.err.println(k + "  " + v);
		});
	}

}
