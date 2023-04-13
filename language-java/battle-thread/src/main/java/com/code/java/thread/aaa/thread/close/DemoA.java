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

/**
 * 通过一个 Boolean 标识位来终止线程，有 BUG
 *
 * @author Snow
 */
@SuppressWarnings("all")
public class DemoA {

	public static void main(String[] args) throws InterruptedException {

		Worker worker = new Worker();

		worker.start();

		Thread.sleep(3000);

		worker.shutdown();

	}

	private static class Worker extends Thread {

		private volatile boolean flag = true;

		@Override
		public void run() {
			// BUG : 如果线程在这里 阻塞 了，此时不能通过 Boolean 或 中断 标识位来结束线程了，怎么办？看后续的代码
			while (flag) {
				// 干活。。。
			}
		}

		public void shutdown() {
			this.flag = false;
		}

	}

}
