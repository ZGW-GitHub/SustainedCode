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

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 加一
 *
 * @author Snow
 * @date 2021/7/13 23:17
 */
public class Demo66 {

	@Test
	void test() {
		Arrays.stream(one(new int[]{9})).forEach(System.out::println);
	}

	public int[] one(int[] digits) {
		// 最后一位不为 9 ，直接将最后一位加一返回
		if (digits[digits.length - 1] != 9) {
			digits[digits.length - 1] += 1;
			return digits;
		}

		// 如果数组长度为 1 ，且最后一位为 9 ，直接返回
		if (digits.length == 1) {
			return new int[]{1, 0};
		}

		// 最后一位为 9 ，将最后一位填为 0 ，继续判断倒数第二位是否为 9
		digits[digits.length - 1] = 0;
		return doPlusOne(digits, digits.length - 2);
	}

	private int[] doPlusOne(int[] digits, int index) {
		// index 位置不为 9 ，加一返回
		if (digits[index] != 9) {
			digits[index] += 1;
			return digits;
		}

		// index 位置为 9 ，将 index 位置填为 0
		digits[index] = 0;

		// index 为第一位 ，创建新数组，返回
		if (index == 0) {
			int[] newDigits = new int[digits.length + 1];
			newDigits[0] = 1;
			return newDigits;
		}

		// index 不是第一位，继续判断
		return doPlusOne(digits, index - 1);
	}

	public int[] two(int[] digits) {
		for (int i = digits.length - 1; i >= 0; i--) {
			if (digits[i] != 9) {
				digits[i] += 1;
				return digits;
			}

			digits[i] = 0;
		}

		int[] newDigits = new int[digits.length + 1];
		newDigits[0] = 1;
		return newDigits;
	}

}
