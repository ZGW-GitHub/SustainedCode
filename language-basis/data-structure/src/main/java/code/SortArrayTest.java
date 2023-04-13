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

package code;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2020/1/5 周日 14:45
 */
public class SortArrayTest {

	private int[] array = null;

	@Test
	void test() {

	}

	@BeforeEach
	public void before() {
		array = SortHelperUtil.generateRandomArray(1000, 0, 99999);

		// 打印原数组
		SortHelperUtil.printArray(array);
	}

	@AfterEach
	public void after() {
		// 打印排序后的数组
		SortHelperUtil.printArray(array);

		// 打印是否有序
		SortHelperUtil.isSort(array);
	}

}
