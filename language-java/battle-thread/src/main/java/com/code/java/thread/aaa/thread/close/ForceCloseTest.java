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

package com.code.java.thread.aaa.thread.close;

import com.code.java.thread.ExceptionUtil;

/**
 * 强制结束线程
 *
 * @author Snow
 */
public class ForceCloseTest {

	public static void main(String[] args) {

		// 开始时间
		long start = System.currentTimeMillis();

		ForceCloseService service = new ForceCloseService();

		service.execute(() -> {
			// 此处模拟 runner 执行了一个耗时为 millis 的工作
			ExceptionUtil.processorVoid(() -> Thread.sleep(15_000));
		});

		service.shutdown(10_000);

		// 结束时间
		long end = System.currentTimeMillis();
		// 计算耗时
		System.out.println(end - start);

	}

}
