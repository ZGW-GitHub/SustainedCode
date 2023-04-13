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

package collection.set_basis.tree_set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

/**
 * @author Snow
 * @date 2020/9/25 5:08 下午
 */
class DemoTest {

	final TreeSet<Integer> set = new TreeSet<>();

	@BeforeEach
	void before() {
		set.add(9);
		set.add(null);
	}

	@Test
	void test() {
	}

	@AfterEach
	void after() {
		System.err.println(set);
	}

}
