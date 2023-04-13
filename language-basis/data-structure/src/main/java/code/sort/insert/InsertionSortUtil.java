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

package code.sort.insert;

import code.SortHelperUtil;

/**
 * 插入排序
 *
 * @author Snow
 * @date 2020/1/5 周日 14:57
 */
public class InsertionSortUtil {

	public static void sort(int[] arr) {

		int len = arr.length;

		for (int i = 1; i < len; i++) {
			for (int j = i; j > 0; j--) {
				if (arr[j] < arr[j - 1]) {
					SortHelperUtil.swap(arr, j, j - 1);
				} else {
					break;
				}
			}
		}

	}

	public static void sortPlus(int[] arr) {
		int len = arr.length;

		for (int i = 1; i < len; i++) {
			int copy = arr[i];
			for (int j = i - 1; j >= 0; j--) {
				if (copy < arr[j]) {
					arr[j + 1] = arr[j];
					if (j == 0) {
						arr[0] = copy;
					}
				} else {
					arr[j + 1] = copy;
					break;
				}
			}
		}

	}

	public static void sortExtent(int[] arr, int l, int r) {

	}

}
