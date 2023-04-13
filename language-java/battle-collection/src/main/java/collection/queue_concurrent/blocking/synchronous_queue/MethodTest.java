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

package collection.queue_concurrent.blocking.synchronous_queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.SynchronousQueue;

/**
 * 有界
 *
 * @author Snow
 */
class MethodTest {

	static final SynchronousQueue<Integer> QUEUE = new SynchronousQueue<>();

	@BeforeEach
	void before() {
		System.err.println(QUEUE.hashCode());
	}

	@Test
	void putTest() throws InterruptedException {
		new Thread(() -> {
			try {
				QUEUE.put(0);
				QUEUE.put(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).start();

		Thread.currentThread().join();
	}

	@Test
	void takeTest() throws InterruptedException {
		new Thread(() -> {
			try {
				System.out.println(QUEUE.take());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).start();

		Thread.currentThread().join();
	}

}
