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

package code.sort.merge;

import code.SortHelperUtil;
import code.sort.insert.InsertionSortUtil;

/**
 * @author Snow
 */
@SuppressWarnings("all")
public class MergeSortPlus {

	public static void mergeSort(int[] arr) {

		System.out.println("归并排序 Plus (User insertionSortPlus)：");
		double time;
		long millis = System.currentTimeMillis();

		_mergeSort(arr, 0, arr.length - 1);

		time = (System.currentTimeMillis() - millis) / 1000f;
		System.out.println(time);

	}

	// 递归使用归并排序，对 arr[l···r] 的范围进行排序
	private static void _mergeSort(int[] arr, int l, int r) {

		if (l >= r)
			return;
		// 优化1: 对于小规模数组, 使用插入排序
		if (r - l <= 10) {
			InsertionSortUtil.sortExtent(arr, l, r);
			return;
		}

		// 优化2: 对于arr[mid] <= arr[mid+1]的情况,不进行merge
		// 对于近乎有序的数组非常有效,但是对于一般情况,有一定的性能损失
		int flag = l + (r - l) / 2;
		_mergeSort(arr, l, flag);
		_mergeSort(arr, flag + 1, r);

		if (arr[flag] > arr[flag + 1])
			MergeSort._merge(arr, l, flag, r);

	}

	public static void main(String[] args) {

		int n = 10000;

		int[] array = SortHelperUtil.generateRandomArray(n, 0, n);

		mergeSort(array);

		SortHelperUtil.isSort(array);

	}

}
