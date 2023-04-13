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

package com.code.java.thread.aaa.thread.start;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 创建线程的两种方式 ：<br/><br/>
 * 一：继承 Thread 类，重写 run() 方法<br/>
 * 1，子类复写父类中的 run() 方法。将线程需要运行的代码放到 run() 中。<br/>
 * 2，创建子类对象的同时线程也被创建。<br/>
 * 3，通过调用 star() 方法开启线程。<br/>
 * <br/>
 * 二：实现 Runnable 接口<br/>
 * 1，定义类实现 Runnable 接口。<br/>
 * 2，覆盖 Runnable 中的 run() 方法。<br/>
 * 3，通过 Thread 类创建线程对象。<br/>
 * 4，将 Runnable 接口的子类对象作为参数传递 Thread 类的构造函数。<br/>
 * 5，调用 Thread 类的 star() 方法开启线程。<br/>
 *
 * @author Snow
 */
public class StartThreadTest {

	@Test
	@SneakyThrows
	void test1() {
		new Thread(() -> System.err.println("Hello Thread")).start();

		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	@SneakyThrows
	void test2() {
		Thread.ofPlatform().name("DemoThread")
				.unstarted(() -> System.err.println("Hello Thread"))
				.start();

		TimeUnit.SECONDS.sleep(3);
	}

}
