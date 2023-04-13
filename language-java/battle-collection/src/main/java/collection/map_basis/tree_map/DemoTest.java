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

package collection.map_basis.tree_map;

import collection.map_basis.enum_map.ColorEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

/**
 * @author Snow
 * @date 2020/9/25 5:07 下午
 */
@Slf4j
class DemoTest {

	final TreeMap<String, Integer> map1 = new TreeMap<>();
	final TreeMap<Integer, String> map2 = new TreeMap<>();

	@BeforeEach
	void before() {
		map1.put(ColorEnum.YELLOW.name(), 2);
		map1.put(ColorEnum.RED.name(), 1);

		map2.put(2, ColorEnum.YELLOW.name());
		map2.put(1, ColorEnum.RED.name());
	}

	@Test
	void test() {
	}

	@AfterEach
	void after() {
		System.err.println(map1);
		System.out.println(map2);
	}

}
