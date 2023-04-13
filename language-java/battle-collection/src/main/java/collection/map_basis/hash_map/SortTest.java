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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Snow
 * @date 2022/12/1 11:41
 */
@Slf4j
public class SortTest {

	Map<String, String> map = new HashMap<>();

	@Test
	void sortByKey() {
		map = new TreeMap<>(map);
	}

	@Test
	void sortByValue() {
		ArrayList<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());

//		list.sort(Comparator.comparing(entry -> entry.getValue()));
//		list.sort(Comparator.comparing(Map.Entry::getValue)); // 简写版本 1
		list.sort(Map.Entry.comparingByValue()); // 简写版本 2
	}

	@BeforeEach
	void beforeEach() {
		map.put("c", "2");
		map.put("a", "1");
		map.put("x", "3");
	}

	@AfterEach
	void afterEach() {
		System.err.println(map);
	}

}
