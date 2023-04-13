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
import code.sort.insert.InsertionSortUtil;

/**
 * 快排优化：
 * 1、使用 插入排序 优化
 * 2、随机选择要比较的元素 k 使得 k 左侧都比 k 小，k 右侧都比 k 大
 * <p>
 * 3、将 等于 要比较的元素分散开
 * <p>
 * 优化了对 近乎有序 的数据的排序
 * 优化了对 大量重复 的数据的排序
 *
 * @author Snow
 */
public class QuickSortPlusTwo {

	public static void quickSort(int[] arr) {

		System.out.println("快速排序 (优化：插入排序,随机比较,重复元素分散) ：");
		double time;
		long millis = System.currentTimeMillis();


		quickSort(arr, 0, arr.length - 1);


		time = (System.currentTimeMillis() - millis) / 1000f;
		System.out.println(time);

	}

	/**
	 * 对 arr[l,r] 进行快速排序
	 */
	private static void quickSort(int[] arr, int l, int r) {

//        if (l >= r)
//            return;
		if (r - l <= 10) {
			// 使用 插入排序 优化
			InsertionSortUtil.sortExtent(arr, l, r);
			return;
		}


		int p = partition(arr, l, r);

		quickSort(arr, l, p - 1);
		quickSort(arr, p + 1, r);

	}

	/**
	 * 对 arr[l,r] 部分进行 partition 操作
	 * 返回p，使得 arr[l, p-1] < arr[p] ；arr[p+1, r] > arr[p]
	 */
	private static int partition(int[] arr, int l, int r) {

		// 随机选择 value
		SortHelperUtil.swap(arr, l, (int) (Math.random() * (r - l + 1)) + l);

		int value = arr[l];
		// arr[l+1, indexl) <= value ; arr(indexr, r] >= value
		int indexl = l + 1;
		int indexr = r;
		while (true) {
			while (arr[indexl] < value && indexl <= r) {
				indexl++;
			}
			while (arr[indexr] > value && indexr >= l + 1) {
				indexr--;
			}
			if (indexl > indexr) {
				break;
			}
			SortHelperUtil.swap(arr, indexl, indexr);
			indexl++;
			indexr--;
		}
		SortHelperUtil.swap(arr, l, indexr);

		return indexr;
	}

}
