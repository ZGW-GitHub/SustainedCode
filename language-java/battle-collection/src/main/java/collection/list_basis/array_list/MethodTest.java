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

package collection.list_basis.array_list;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Snow
 * @date 2020/9/3 11:41 上午
 */
class MethodTest {

	final List<String> list = new ArrayList<>();

	@Test
	void retainAllTest() {
		List<String> str1 = new ArrayList<>();
		str1.add("a");
		str1.add("b");
		str1.add("c");

		List<String> str2 = new ArrayList<>();
		str2.add("d");
		str2.add("b");
		str2.add("f");

		System.out.println(Arrays.toString(str1.toArray()));
		System.out.println(Arrays.toString(str2.toArray()));

		System.out.println(str1.retainAll(str2));

		System.out.println(Arrays.toString(str1.toArray()));
		System.out.println(Arrays.toString(str2.toArray()));
	}

	@Test
	void reverseTest() {
		list.add("a");
		list.add("b");
		list.add("c");

		list.forEach(System.err::println);

		Collections.reverse(list);

		list.forEach(System.err::println);
	}

}
