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

package collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Snow
 * @date 2021/6/7 11:43
 */
@Slf4j
class UnmodifiableTest {

	final List<String> list = new ArrayList<>();

	@BeforeEach
	void before() {
		list.add("a");
	}

	@Test
	void test() {
		List<String> unmodifiable = Collections.unmodifiableList(list);

		unmodifiable.forEach(System.err::println);

		list.add("3");
	}

}
