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

package com.code.reference.weak.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @author Snow
 * @date 2022/11/30 17:06
 */
@Slf4j
public class GcTest {

	final static int                      count = 1000000;
	final static ArrayList<WeakReference> list  = new ArrayList<>(count);

	static Object obj = new Object();

	/**
	 * WeakReference 对象并不会被回收，obj 会被回收
	 */
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < count; i++) {
			list.add(new WeakReference<>(obj));
		}

		System.err.println("创建完成");
		obj = null;
		Thread.currentThread().join();
	}

}
