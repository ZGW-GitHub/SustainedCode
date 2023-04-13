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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 有界
 *
 * @author Snow
 */
class MethodTest {

	final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);

	@BeforeEach
	void before() {
		queue.add(1);
		queue.add(2);
	}

	/**
	 * 将队列中的值复制到一个容器
	 */
	@Test
	void drainToTest() {
		final List<Integer> list = new ArrayList<>();

		queue.drainTo(list);

		System.err.println(list);
	}

	/**
	 * 剩余容量
	 */
	@Test
	void testRemainingCapacity() {
		System.out.println(queue.remainingCapacity()); // 2

		queue.add(1);
		System.out.println(queue.remainingCapacity()); // 1

		queue.add(2);
		System.out.println(queue.remainingCapacity()); // 0
	}

	@AfterEach
	void after() {
		System.err.println(queue);
	}

}
