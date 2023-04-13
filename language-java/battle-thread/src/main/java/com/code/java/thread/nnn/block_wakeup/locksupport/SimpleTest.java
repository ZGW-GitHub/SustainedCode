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

package com.code.java.thread.nnn.block_wakeup.locksupport;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * API：
 * <br />{@link LockSupport#park() park()} —— 阻塞当前线程，除非许可证可用
 * <br />{@link LockSupport#unpark(Thread) unpark(Thread)} —— 将指定线程的许可证设为可用
 *
 * @author Snow
 * @date 2020/5/11 9:14 上午
 */
@Slf4j
@SuppressWarnings("all")
public class SimpleTest {

	@Test
	void demoTest() throws InterruptedException {
		Thread t1 = new Thread(this::work);
		t1.start();

		TimeUnit.SECONDS.sleep(1);

		System.err.println("开始线程唤醒");
		System.err.println(LockSupport.getBlocker(t1));
		LockSupport.unpark(t1);
		System.err.println("结束线程唤醒");

		TimeUnit.SECONDS.sleep(3);
	}

	private void work() {
		ExceptionUtil.processorVoid(() -> {
			System.err.println("开始线程阻塞");
			LockSupport.park(this);
			System.err.println("结束线程阻塞");
		});
	}

}
