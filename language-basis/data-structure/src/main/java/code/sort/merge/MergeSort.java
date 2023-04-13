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

/**
 * 归并排序
 *
 * @author Snow
 */
public class MergeSort {

	public static void mergeSort(int[] arr) {

		System.out.println("归并排序：");
		double time;
		long millis = System.currentTimeMillis();

		_mergeSort(arr, 0, arr.length - 1);

		time = (System.currentTimeMillis() - millis) / 1000f;
		System.out.println(time);

	}

	/**
	 * 递归使用归并排序，对 arr[l···r] 的范围进行排序
	 */
	private static void _mergeSort(int[] arr, int l, int r) {
		if (l >= r) {
			return;
		}

		int flag = l + (r - l) / 2;
		_mergeSort(arr, l, flag);
		_mergeSort(arr, flag + 1, r);
		_merge(arr, l, flag, r);
	}

	/**
	 * 将 arr[l···flag] 与 arr[flag+1 ··· r] 两部分进行合并
	 */
	public static void _merge(int[] arr, int l, int flag, int r) {
		int[] copy = new int[r - l + 1];
		for (int i = l; i <= r; i++) {
			copy[i - l] = arr[i];
		}

		int i = l;
		int j = flag + 1;
		for (int k = l; k <= r; k++) {
			if (i > flag) {
				arr[k] = copy[j - l];
				j++;
			} else if (j > r) {
				arr[k] = copy[i - l];
				i++;
			} else if (copy[i - l] < copy[j - l]) {
				arr[k] = copy[i - l];
				i++;
			} else {
				arr[k] = copy[j - l];
				j++;
			}
		}
	}

}
