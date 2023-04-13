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

package com.code.java.thread.aaa.thread.method;


import org.junit.jupiter.api.Test;

/**
 * 让掉当前线程 CPU 的时间片，使正在运行中的线程重新变成就绪状态，并重新竞争 CPU 的调度权。
 * 它可能会获取到，也有可能被其他线程获取到。
 *
 * @author Snow
 */
@SuppressWarnings("all")
public class YieldTest {

	@Test
	void test(String[] args) {
		new Thread(() -> {
			for (int i = 0; i <= 100; i++) {
				System.out.println(i);
				if (i == 50) {
					Thread.yield();
				}
			}
		}).start();

		new Thread(() -> {
			for (int i = 0; i <= 10; i++) {
				System.out.println("======");
			}
		}).start();
	}

}
