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

package code.sort.bubble;

import code.SortHelperUtil;

/**
 * 冒泡排序
 *
 * @author Snow
 */
public class BubbleSortUtil {

	public static void sort(int[] arr) {
		int len = arr.length;

		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					SortHelperUtil.swap(arr, j, j + 1);
				}
			}
		}
	}

	/**
	 * 优化
	 *
	 * @param arr 数组
	 */
	public static void bubbleSort(int[] arr) {
		int n = arr.length;

		for (int i = 0; i < n; i++) {
			int j = 0;
			// 使用 copy(备份元素) 替换元素的位置对调操作
			int copy = arr[j];
			for (; j < n - i - 1; j++) {
				// 这里考虑了当两数相等时，不进行操作，以此减少数据的总操作次数，避免浪费
				if (copy > arr[j + 1]) {
					arr[j] = arr[j + 1];
				} else if (copy < arr[j + 1]) {
					arr[j] = copy;
					copy = arr[j + 1];
				}
			}
		}
	}

}
