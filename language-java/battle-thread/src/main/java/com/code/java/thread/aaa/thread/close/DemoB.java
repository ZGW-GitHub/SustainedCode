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
 * 通过 中断标识位 来终止线程，有 BUG
 *
 * @author Snow
 */
public class DemoB {

	public static void main(String[] args) {
		Worker worker = new Worker();
		worker.start();

		ExceptionUtil.processorVoid(() -> Thread.sleep(1_000));

		worker.interrupt();
	}

	private static class Worker extends Thread {

		@Override
		public void run() {
			while (true) {
				if (Thread.interrupted()) {
					break;
				}
				// 干活
			}
			// 善后：当被中断可以跳出循环，就能执行“善后”工作了 。
		}

	}

}
