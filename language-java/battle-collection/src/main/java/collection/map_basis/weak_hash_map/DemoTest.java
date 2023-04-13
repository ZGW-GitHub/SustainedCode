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

package collection.map_basis.weak_hash_map;

import collection.map_basis.enum_map.ColorEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.WeakHashMap;

/**
 * @author Snow
 * @date 2021/5/28 14:41
 */
@Slf4j
class DemoTest {

	final WeakHashMap<String, Integer> map = new WeakHashMap<>();

	@BeforeEach
	void before() {
		map.put(ColorEnum.RED.name(), 1);
		map.put(ColorEnum.RED.name(), 2);
	}

	@Test
	void test() {
	}

	@AfterEach
	void after() {
		System.err.println(map);
	}

}
