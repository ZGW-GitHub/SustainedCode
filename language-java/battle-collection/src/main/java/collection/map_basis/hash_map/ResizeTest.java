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

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 扩容测试
 *
 * @author Snow
 * @date 2020/8/14 11:29 上午
 */
class ResizeTest {

	final Map<Integer, Integer> map = new HashMap<>(0);

	@Test
	void test() {
		IntStream.range(1, 64).forEach(i -> {
			map.put(i, i);

			System.err.println("put i : " + i);
		});
	}

}
