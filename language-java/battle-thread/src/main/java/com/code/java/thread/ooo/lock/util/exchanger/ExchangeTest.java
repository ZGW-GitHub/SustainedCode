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

package com.code.java.thread.ooo.lock.util.exchanger;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * API：
 * <br />{@link Exchanger#exchange(Object) exchange(Object obj)} —— 交换数据并等待对方调用exchange
 *
 * @author Snow
 */
@SuppressWarnings("all")
@Slf4j
public class ExchangeTest {

	private final Exchanger<String> exchanger = new Exchanger<>();

	@Test
	void test() throws InterruptedException {
		new Thread(this::doExchange, "T1").start();
		new Thread(this::doExchange, "T2").start();

		TimeUnit.SECONDS.sleep(2);
	}

	public void doExchange() {
		try {
			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(1));

			String result = exchanger.exchange("my name is " + Thread.currentThread().getName());

			System.err.println(Thread.currentThread().getName() + " 收到：" + result);
		} catch (Exception e) {
			log.error("发生异常：{}", e.getMessage(), e);
		}
	}

}
