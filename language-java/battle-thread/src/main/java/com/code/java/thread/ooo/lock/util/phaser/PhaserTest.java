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

package com.code.java.thread.ooo.lock.util.phaser;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * API：
 * <br />{@link Phaser#getPhase() getPhase()} —— 获取当前阶段
 * <br />{@link Phaser#register() register()} —— 注册一个参与者
 * <br />{@link Phaser#arriveAndAwaitAdvance() arriveAndAwaitAdvance()} —— 等待其它参与者到达
 *
 * @author Snow
 */
@Slf4j
@SuppressWarnings("all")
public class PhaserTest {

	@Test
	void baseTest() {
		final Phaser phaser = new Phaser();

		startNewThread(phaser, 1, "T1");
		startNewThread(phaser, 2, "T2");
		startNewThread(phaser, 3, "T3");

		System.err.println(phaser.getPhase()); // 获取当前阶段

		phaser.register(); // 注册一个参与者
		phaser.arriveAndAwaitAdvance(); // 等待其它参与者到达

		System.err.println(phaser.getPhase()); // 获取当前阶段

		System.err.println("over !");
	}

	@Test
	void baseTest2() {
		final Phaser phaser = new Phaser();

		startNewThread(phaser, 1, "T1");
		startNewThread(phaser, 2, "T2");
		startNewThread(phaser, 3, "T3");

		System.err.println("当前 Phaser ：" + phaser.getPhase()); // 获取当前阶段

		phaser.register(); // 注册一个参与者
		phaser.arriveAndAwaitAdvance(); // 等待其它参与者到达

		System.err.println("当前 Phaser ：" + phaser.getPhase()); // 获取当前阶段

		System.err.println("over !");
	}

	private void startNewThread(Phaser phaser, int sleepSeconds, String threadName) {
		new Thread(() -> {
			phaser.register(); // 注册一个参与者

			ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(sleepSeconds));

			System.err.println(Thread.currentThread().getName() + " 阶段 over ");

			phaser.arriveAndAwaitAdvance(); // 等待其它参与者到达
		}, threadName).start();
	}

}

