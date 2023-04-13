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

package other.xianliu;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流算法：固定窗口算法
 *
 * @author Snow
 * @date 2021/9/17 22:47
 */
@Slf4j
public class CounterLimiter {

	private int windowSize; //窗口大小，毫秒为单位
	private int limit;//窗口内限流大小
	private AtomicInteger count;//当前窗口的计数器

	private CounterLimiter() {}

	public CounterLimiter(int windowSize, int limit) {
		this.limit = limit;
		this.windowSize = windowSize;
		count = new AtomicInteger(0);

		//开启一个线程，达到窗口结束时清空count
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					count.set(0);
					try {
						Thread.sleep(windowSize);
					} catch (InterruptedException e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}).start();
	}

	//请求到达后先调用本方法，若返回true，则请求通过，否则限流
	public boolean tryAcquire() {
		int newCount = count.addAndGet(1);
		if (newCount > limit) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		//每秒20个请求
		CounterLimiter counterLimiter = new CounterLimiter(1000, 20);
		int count = 0;
		//模拟50次请求，看多少能通过
		for (int i = 0; i < 50; i++) {
			if (counterLimiter.tryAcquire()) {
				count++;
			}
		}
		System.out.println("第一拨50次请求中通过：" + count + ",限流：" + (50 - count));
		//过一秒再请求
		Thread.sleep(1000);
		//模拟50次请求，看多少能通过
		count = 0;
		for (int i = 0; i < 50; i++) {
			if (counterLimiter.tryAcquire()) {
				count++;
			}
		}
		System.out.println("第二拨50次请求中通过：" + count + ",限流：" + (50 - count));
	}

}
