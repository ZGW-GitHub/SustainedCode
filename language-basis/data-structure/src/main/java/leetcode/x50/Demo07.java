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
 * 整数反转
 *
 * @author Snow
 * @date 2021/5/6 10:15
 */
public class Demo07 {

	public int one(int x) {
		long n = 0;
		while (x != 0) {
			n = n * 10 + x % 10;
			x = x / 10;
		}
		return (int) n == n ? (int) n : 0;
	}

	public int two(int x) {
		String numStr = String.valueOf(x);

		int flag = 1;
		if (numStr.contains("-")) {
			flag = -1;
		}

		try {
			return Integer.parseInt(new StringBuilder(numStr).reverse().toString()) * flag;
		} catch (Exception e) {
			return 0;
		}
	}

}
