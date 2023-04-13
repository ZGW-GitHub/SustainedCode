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
 * Thread 类的实例方法：interrupt()		设置中断标记<br/>
 * Thread 类的实例方法：isInterrupted()	不清除中断标记<br/>
 * Thread 类的静态方法：interrupted()		清除中断标记
 *
 * @author Snow
 */
public class InterruptedTest {

	@Test
	void test() throws InterruptedException {
		Thread t = new Thread(() -> {
			ExceptionUtil.processorVoid(() -> Thread.sleep(3000), ">> 收到中断信号");

			System.out.println("中断之后，可继续做事！");
		});

		t.start();
		Thread.sleep(1000);

		// false : 线程未被中断
		System.out.println("中断前，t.isInterrupted() ：" + t.isInterrupted());
		// false : 线程未被中断，不用清除标记
		System.out.println("中断前，Thread.interrupted() ：" + Thread.interrupted());

		// 通知线程中断
		t.interrupt();

		// 下面四句输出别同时打开，因为重排序导致它们的执行顺序不一定，从而导致结果不是预期的，可以每次打开一组

		// 都应该返回 true ，但因 InterruptedException 异常被捕获，所以这里都返回 false
		System.out.println("中断后，t.isInterrupted() ：" + Thread.currentThread().isInterrupted());
		System.out.println("中断后，t.isInterrupted() ：" + Thread.currentThread().isInterrupted());

		// 第一个应返回 true，第二个应返回 false ，同样因为 InterruptedException 异常被捕获，所以这里都返回 false
		System.out.println("中断后，Thread.interrupted() ：" + Thread.interrupted());
		System.out.println("中断后，Thread.interrupted() ：" + Thread.interrupted());
	}

}
