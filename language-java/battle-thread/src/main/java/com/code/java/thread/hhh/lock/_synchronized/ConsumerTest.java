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

package com.code.java.thread.hhh.lock._synchronized;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2020/5/7 2:28 下午
 */
@Slf4j
public class ConsumerTest {

	/**
	 * 锁对象
	 */
	public final Object MONITOR = ConsumerTest.class;

	/**
	 * 用来存储生产的数据
	 */
	private          int     num    = 0;
	/**
	 * 标识是否有已生产的数据
	 */
	private volatile boolean isHave = false;

	/**
	 * 一个生产者、消费者示例
	 */
	@Test
	void consumerTest() throws InterruptedException {
		new Thread(this::provider, "T1").start();
//		new Thread(this::provider, "T2").start();
		new Thread(this::consumer, "T3").start();
//		new Thread(this::consumer, "T4").start();

		Thread.currentThread().join();
	}

	private void provider() {
		synchronized (MONITOR) {
			while (true) {
				ExceptionUtil.processorVoid(() -> {
					while (isHave) {
						MONITOR.wait();
					}

					TimeUnit.SECONDS.sleep(1);
					System.err.println(Thread.currentThread().getName() + "，生产了 ：" + ++num);

					isHave = true;
				}, "生产者发生异常");

				MONITOR.notifyAll();
			}
		}
	}

	private void consumer() {
		synchronized (MONITOR) {
			while (true) {
				ExceptionUtil.processorVoid(() -> {
					while (!isHave) {
						MONITOR.wait();
					}

					TimeUnit.SECONDS.sleep(1);
					System.err.println(Thread.currentThread().getName() + "，消费了 ：" + num);

					isHave = false;
				}, "消费者发生异常");

				MONITOR.notifyAll();
			}
		}
	}

}
