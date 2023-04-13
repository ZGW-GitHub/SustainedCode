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

package com.code.java.thread.ooo.lock.util.countdownlatch;

import cn.hutool.core.util.RandomUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Snow
 * @date 2022/11/27 23:46
 */
@Slf4j
public class BatchTaskTest {

	/**
	 * 需要批处理的任务
	 */
	final List<Integer>  integerList    = taskList();
	/**
	 * 下个要处理的任务
	 */
	final AtomicInteger  atomicInteger  = new AtomicInteger();
	/**
	 * 批处理大小
	 */
	final int            batchNum       = 10;
	/**
	 * 执行批处理的线程数
	 */
	final int            threadNum      = 10;
	/**
	 * 工具
	 */
	final CountDownLatch countDownLatch = new CountDownLatch(threadNum);

	@Test
	@SneakyThrows
	void test() {
		for (int i = 0; i < threadNum; i++) {
			new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(1);

					for (int j = 0; j < batchNum; j++) {
						Integer task = integerList.get(atomicInteger.getAndIncrement());
						TimeUnit.MILLISECONDS.sleep(RandomUtil.randomInt(200, 800));
						System.err.printf("线程[%s] 处理了任务：%s\n", Thread.currentThread().getName(), task);
					}

					countDownLatch.countDown();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}, "Thread-" + i).start();
		}

		System.err.println("等待...");
		countDownLatch.await();
		System.err.printf("结束了：%s", atomicInteger.get());
	}

	List<Integer> taskList() {
		List<Integer> integerList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			integerList.add(i);
		}
		return integerList;
	}

}
