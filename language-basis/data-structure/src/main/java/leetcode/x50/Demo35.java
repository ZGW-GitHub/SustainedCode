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

package leetcode.x50;

/**
 * 搜索插入位置
 *
 * @author Snow
 * @date 2021/5/6 10:13
 */
public class Demo35 {

	/**
	 * 普通
	 */
	public int one(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > target || nums[i] == target) {
				return i;
			}
		}

		return nums.length;
	}

	/**
	 * 二分查找法
	 */
	public int two(int[] nums, int target) {
		int left = 0;
		int right = nums.length;

		while (left < right) {
			int min = (left + right) / 2;

			if (nums[min] == target) {
				return min;
			} else if (nums[min] < target) {
				left = min + 1;
			} else {
				right = min;
			}
		}

		return left;
	}

}
