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
 * 自底向上的归并排序
 *
 * @author Snow
 */
public class MergeSortTwo {

	public static void mergeSort(int[] arr) {

		System.out.println("归并排序(自底向上)：");
		double time;
		long millis = System.currentTimeMillis();

		for (int sz = 1; sz < arr.length; sz += sz) {
			for (int i = 0; i + sz < arr.length; i += (2 * sz)) {
				// 对 arr[i, i+sz-1] 和 arr[i+sz, i+sz+sz-1] 进行归并
				_merge(arr, i, i + sz - 1, Math.min(i + 2 * sz - 1, arr.length - 1));
			}
		}

		time = (System.currentTimeMillis() - millis) / 1000f;
		System.out.println(time);

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
