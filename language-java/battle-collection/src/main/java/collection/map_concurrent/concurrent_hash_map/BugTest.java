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

package collection.map_concurrent.concurrent_hash_map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Snow
 */
class BugTest {

	final ConcurrentHashMap<Integer, Integer> hashMap = new ConcurrentHashMap<>(0);

	@Test
	void bugTest() {
		Map<Object, Object> map = new ConcurrentHashMap<>(16);

		map.computeIfAbsent("key1", key1 -> map.computeIfAbsent("key2", key2 -> "test"));
	}

	@AfterEach
	void after() {
		hashMap.forEach((k, v) -> System.err.println(k + " --- " + v));
	}

}
