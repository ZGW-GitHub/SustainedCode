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

package code.sort.quick;

import code.SortHelperUtil;

/**
 * 快排
 *
 * @author Snow
 */
public class QuickSort {

	public static void quickSort(int[] arr) {

		System.out.println("快速排序：");
		double time;
		long millis = System.currentTimeMillis();


		_quickSort(arr, 0, arr.length - 1);


		time = (System.currentTimeMillis() - millis) / 1000f;
		System.out.println(time);

	}

	// 对 arr[l,r] 进行快速排序
	private static void _quickSort(int[] arr, int l, int r) {

		if (l >= r)
			return;

		int p = _partition(arr, l, r);

		_quickSort(arr, l, p - 1);
		_quickSort(arr, p + 1, r);

	}

	// 对 arr[l,r] 部分进行 partition 操作
	// 返回p，使得 arr[l, p-1] < arr[p] ；arr[p+1, r] > arr[p]
	private static int _partition(int[] arr, int l, int r) {

		int value = arr[l];
		int j = l;

		// arr[l+1, j] < value      arr[j+1, i) > v
		for (int index = l + 1; index <= r; index++) {
			if (arr[index] < value) {
				SortHelperUtil.swap(arr, j + 1, index);
				j++;
			}
		}

		SortHelperUtil.swap(arr, l, j);

		return j;

	}

}
