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

package leetcode.x200;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2022/1/4 11:25 AM
 */
@Slf4j
@SuppressWarnings("all")
public class Demo167 {
	public int[] twoSum(int[] numbers, int target) {

		int low = 0, high = numbers.length - 1;
		while (low < high) {
			int sum = numbers[low] + numbers[high];
			if (sum == target) {
				return new int[]{low + 1, high + 1};
			} else if (sum < target) {
				++low;
			} else {
				--high;
			}
		}
		return new int[]{-1, -1};
		
	}
}
