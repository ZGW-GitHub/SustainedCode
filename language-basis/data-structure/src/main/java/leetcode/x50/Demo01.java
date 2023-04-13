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

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 *
 * @author Snow
 * @date 2021/5/5 18:39
 */
public class Demo01 {

	/**
	 * 暴力
	 */
	public int[] one(int[] nums, int target) {
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
					return new int[]{i, j};
				}
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * 两次 Hash 法
	 */
	public int[] two(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>(0);

		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], i);
		}

		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];
			if (map.containsKey(complement) && map.get(complement) != i) {
				return new int[]{map.get(complement), i};
			}
		}

		throw new IllegalArgumentException();
	}

	/**
	 * 一次 Hash 法
	 */
	public int[] three(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];

			if (map.containsKey(complement) && map.get(complement) != i) {
				return new int[]{map.get(complement), i};
			}

			map.put(nums[i], i);
		}

		throw new IllegalArgumentException();
	}

}
