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

/**
 * @author Snow
 */
public class SortHelperUtil {

	/**
	 * 生成有 n 个元素的随机数组,每个元素的随机范围为 ：[rangeL, rangeR]
	 * @param n      数组大小
	 * @param rangeL 随机数的左边界
	 * @param rangeR 随机数的右边界
	 */
	public static int[] generateRandomArray(int n, int rangeL, int rangeR) {
		System.out.println("Test : arr_size = " + n + " , random : [ " + rangeL + " , " + rangeR + " ]\n");

		if (rangeL > rangeR) {
			throw new RuntimeException("rangeL > rangeR");
		}

		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * (rangeR - rangeL + 1) + rangeL);
		}

		return arr;
	}

	/**
	 * 生成一个近乎有序的数组
	 * 首先生成一个含有 [0...n-1] 的完全有序数组, 之后随机交换 swapTimes 对数据
	 * swapTimes定义了数组的无序程度:
	 * swapTimes == 0 时, 数组完全有序
	 * swapTimes 越大, 数组越趋向于无序
	 */
	public static int[] generateNearlyOrderedArray(int n, int swapTimes) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = i;
		}

		for (int i = 0; i < swapTimes; i++) {
			int a = (int) (Math.random() * n);
			int b = (int) (Math.random() * n);
			int t = arr[a];
			arr[a] = arr[b];
			arr[b] = t;
		}

		return arr;
	}


	/**
	 * 打印数组的所有元素
	 */
	public static void printArray(int[] arr) {
		int num = 0;

		for (int value : arr) {
			if (value < 100) {
				num += 1;
			}
			System.out.print(value);
			System.out.print(' ');
		}

		System.out.println("\n小于 100 的数有 ：" + num + "个。\n");
	}

	/**
	 * 判断数组是否有序
	 */
	public static void isSort(int[] arr) {
		System.out.print("是否有序 : ");

		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1]) {
				System.out.println(false);
			}
		}

		System.out.println(true);
	}

	/**
	 * 交换位置
	 */
	public static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

}
