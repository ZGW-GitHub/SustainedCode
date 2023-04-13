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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * 循环删除 ArrayList 中指定元素
 *
 * @author Snow
 * @date 2020/7/17 4:21 下午
 */
class RemoveTest {

	final ArrayList<String> list = new ArrayList<>();

	final Predicate<String> predicate = p -> "a".equals(p) || "b".equals(p);

	@BeforeEach
	void init() {
		list.add("a");
		list.add("b");
		list.add("c");
	}

	/**
	 * 使用 removeIf() 底层使用的是迭代器循环
	 * 正确
	 */
	@Test
	void deleteByMethod() {
		list.removeIf(predicate);
	}

	/**
	 * 迭代器循环
	 * 正确
	 */
	@Test
	void deleteByIterator() {
		Iterator<String> iterator = list.iterator();

		while (iterator.hasNext()) {
			if (predicate.test(iterator.next())) {
				iterator.remove();
			}
		}
	}

	/**
	 * 调用批量删除方法
	 * 正确
	 */
	@Test
	void deleteAll() {
		List<String> removeList = new ArrayList<>();

		for (String string : list) {
			if (predicate.test(string)) {
				removeList.add(string);
			}
		}

		list.removeAll(removeList);
	}

	/**
	 * 普通for循环倒序删除
	 * 正确
	 */
	@Test
	void deleteByReverseOrder() {
		for (int i = list.size() - 1; i >= 0; i--) {
			if (predicate.test(list.get(i))) {
				list.remove(list.get(i));
			}
		}
	}

	// --------------------------- 以下为结果不正确 ---------------------------

	/**
	 * 普通for循环正序删除
	 */
	@Test
	void deleteByOrder() {
		for (int i = 0; i < list.size(); i++) {
			if (predicate.test(list.get(i))) {
				list.remove(list.get(i));
			}
		}
	}

	/**
	 * 迭代器循环，使用 ArrayList 的 remove() 方法删除
	 */
	@Test
	void deleteByArrayList() {
		Iterator<String> iterator = list.iterator();

		while (iterator.hasNext()) {
			if (predicate.test(iterator.next())) {
				list.remove(iterator.next());
			}
		}
	}

	// --------------------------- 以下为抛异常（ java.util.ConcurrentModificationException ）---------------------------

	/**
	 * java8 forEach 方法删除
	 */
	@Test
	void deleteByForeach() {
		list.forEach(p -> {
			if (predicate.test(p)) {
				list.remove(p);
			}
		});
	}

	/**
	 * 增强版for循环删除
	 */
	@Test
	void deleteByEnhancedForLoop() {
		for (String string : list) {
			if (predicate.test(string)) {
				list.remove(string);
			}
		}
	}

	@AfterEach
	void destroy() {
		System.out.println(list);
	}

}
