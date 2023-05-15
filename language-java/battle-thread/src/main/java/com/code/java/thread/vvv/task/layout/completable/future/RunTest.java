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

package com.code.java.thread.vvv.task.layout.completable.future;

import com.code.java.thread.ExceptionUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2020/4/20 11:56 上午
 */
public class RunTest {

	public static int getData() {
		System.out.println("begin");

		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));

		System.out.println("end");
		System.out.println("------------\n");

		return 100;
	}

	public static String getStringData() {
		System.out.println("begin");

		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(1));

		System.out.println("end");
		System.out.println("------------\n");

		return "100";
	}

	public static int throwException() {
		System.out.println("准备抛出异常");

		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(2));

		System.out.println("抛了");
		System.out.println("------------\n");

		throw new RuntimeException("主动抛出异常");
	}

}
