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

package collection.queue_concurrent.blocking.array_blocking_queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 有界
 *
 * @author Snow
 */
class DemoTest {

	final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(99);

	@BeforeEach
	void before() {
		queue.add(7);
		queue.add(9);
	}

	@Test
	void test() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			System.err.println(queue.take());

			System.err.println("Next...");
		}
	}

	@AfterEach
	void after() {
		System.err.println(queue);
	}

}
