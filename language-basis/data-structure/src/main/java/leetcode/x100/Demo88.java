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

package leetcode.x100;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 合并两个有序数组
 *
 * @author Snow
 * @date 2021/7/15 17:52
 */
@Slf4j
public class Demo88 {

	@Test
	void test() {
		one(new int[]{1, 5, 2}, 3, new int[]{6, 3}, 2);
	}

	public void one(int[] nums1, int m, int[] nums2, int n) {
		int index1    = m - 1;
		int index2    = n - 1;
		int lastIndex = m + n - 1;

		while (index1 >= 0 && index2 >= 0) {
			if (nums1[index1] > nums2[index2]) {
				nums1[lastIndex] = nums1[index1];
				index1--;
			} else {
				nums1[lastIndex] = nums2[index2];
				index2--;
			}
			lastIndex--;
		}

		if (index2 >= 0) {
			System.arraycopy(nums2, 0, nums1, 0, index2 + 1);
		}
	}

}
