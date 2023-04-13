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

package com.code.reference.weak.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/11/30 17:10
 */
@Slf4j
public class ReferenceQueueTest {

	final static List<WeakReference> list = new ArrayList<>(10);

	final static ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

	static Object obj = new Object();

	public static void main(String[] args) throws InterruptedException {
		// 测试消费 ReferenceQueue 中的数据
		testConsumerReferenceQueue();

		// 测试回收 WeakReference 对象
//		testWeakReferenceGc();

		Thread.currentThread().join();
	}

	static void testConsumerReferenceQueue() {
		for (int i = 0; i < 5; i++) {
			list.add(new WeakReference<>(obj, referenceQueue));
		}

		new Thread(() -> {
			while (true) {
				try {
					Reference<?> reference = referenceQueue.remove();
					System.err.printf("弱引用：%s \n", reference);

					Object innerObj = reference.get();
					System.err.printf("弱引用所引用的对象：%s \n", innerObj);

					System.err.println("T1 WeakReference List : " + list);

					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();

		obj = null;
	}

	static void testWeakReferenceGc() {
		for (int i = 0; i < 1000; i++) {
			list.add(new WeakReference<>(obj, referenceQueue));
		}

		new Thread(() -> {
			int count = 0;
			while (true) {
				try {
					Reference<?> reference = referenceQueue.remove();
//					System.err.printf("弱引用：%s \n", reference);

					Object innerObj = reference.get();
//					System.err.printf("弱引用所引用的对象：%s \n", innerObj);

					list.remove(reference);

					System.err.println(count += 1);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();

		obj = null;
	}

}
