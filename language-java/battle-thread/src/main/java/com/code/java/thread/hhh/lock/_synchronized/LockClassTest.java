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

/**
 * @author Snow
 * @date 2022/11/28 18:16
 */
@Slf4j
public class LockClassTest {

	/**
	 * 锁对象
	 */
	public final Object MONITOR = ConsumerTest.class;

	@Test
	void demoTest() throws InterruptedException {
		new Thread(this::work, "T1").start();
		new Thread(this::work, "T2").start();

		Thread.currentThread().join(1000 * 10);
	}

	private void work() {
		synchronized (MONITOR) {
			ExceptionUtil.processorVoid(() -> {
				System.out.println(Thread.currentThread().getName() + " 抢到了锁！");

				Thread.sleep(3_000);

				System.out.println(Thread.currentThread().getName() + " 释放了锁！");
			});
		}
	}

}
