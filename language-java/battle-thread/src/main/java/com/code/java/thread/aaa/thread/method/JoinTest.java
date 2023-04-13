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

import com.code.java.thread.ExceptionUtil;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * t1 执行了 t2.join() 则 t1 会等待 t2 执行结束后再继续执行，在 t2 执行时可以通过 t3 对 t1 执行 t1.interrupt() 来使得 t1 不再等待 t2
 */
@SuppressWarnings("all")
public class JoinTest {

	@Test
	void test() {
		Thread main = Thread.currentThread();

		Thread t1 = new Thread(() -> {
			while (true) {

			}
		});

		Thread t2 = new Thread(() -> {
			ExceptionUtil.processorVoid(() -> Thread.sleep(1000));

			// 通过 t2 打断 main 的等待，来使得 main 继续执行
			main.interrupt();
		});

		t1.start();
		t2.start();

		try {
			System.out.println("即将 join");
			t1.join(); // main 会在此等待 t 执行完再继续执行
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("main 执行到了这里！");
		}

		System.out.println("main 执行完了！ ");
	}

}
