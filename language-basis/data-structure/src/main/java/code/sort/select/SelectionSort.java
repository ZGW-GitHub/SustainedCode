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

package code.sort.select;

import code.SortHelperUtil;

/**
 * 选择排序
 *
 * @author Snow
 * @date 2020/1/5 周日 14:18
 */
public class SelectionSort {

	public static void sort(int[] arr) {

		int len = arr.length;

		for (int i = 0; i < len; i++) {
			int minIndex = i;
			for (int j = i + 1; j < len; j++) {
				if (arr[minIndex] > arr[j]) {
					minIndex = j;
				}
			}
			SortHelperUtil.swap(arr, i, minIndex);
		}
	}

}
