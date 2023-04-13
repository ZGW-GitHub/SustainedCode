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

package com.code.java.thread.ooo.lock.util.semaphore;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * API：
 * <br />{@link Semaphore#acquire(int) acquire(int)} —— 从信号量获取给定数量的许可证，会阻塞直至许可证获取成功
 * <br />{@link Semaphore#release(int) release(int)} —— 释放给定数量的许可证
 *
 * @author Snow
 */
@Slf4j
@SuppressWarnings("all")
public class ConsumerTest {

	private final Semaphore semaphore = new Semaphore(2);

	// 标识是否有已生产的数据
	private volatile boolean isHave = false;
	// 用来存储生产的数据
	private          int     num    = 0;

	/**
	 * 一个生产者、消费者示例
	 */
	@Test
	void consumerTest() throws InterruptedException {
		new Thread(this::provider, "T1").start();
		new Thread(this::consumer, "T2").start();

		Thread.currentThread().join();
	}

	private void provider() {
		while (true) {
			ExceptionUtil.processorVoid(() -> {
				semaphore.acquire(2); // 从信号量获取许可证，获取不到阻塞在这里
				if (isHave) {
					semaphore.release(2);  // 释放许可证
					TimeUnit.SECONDS.sleep(1);
				} else {
					TimeUnit.SECONDS.sleep(1);
					System.err.println(Thread.currentThread().getName() + "，生产了 ：" + ++num);

					isHave = true;
					semaphore.release(2); // 释放许可证
				}
			}, "生产者发送异常");
		}
	}

	private void consumer() {
		while (true) {
			ExceptionUtil.processorVoid(() -> {
				semaphore.acquire(2);
				if (!isHave) {
					semaphore.release(2);
					TimeUnit.SECONDS.sleep(1);
				} else {
					TimeUnit.SECONDS.sleep(1);
					System.err.println(Thread.currentThread().getName() + "，消费了 ：" + num);

					isHave = false;
					semaphore.release(2);
				}
			}, "消费者发送异常");
		}
	}

}
