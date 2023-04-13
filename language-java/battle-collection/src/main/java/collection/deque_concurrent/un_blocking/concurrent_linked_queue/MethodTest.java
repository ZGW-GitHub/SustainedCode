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

package collection.deque_concurrent.un_blocking.concurrent_linked_queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Snow
 */
class MethodTest {

	final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

	@BeforeEach
	void before() {
		queue.add("aaa");
		queue.add("bbb");
		queue.add("ccc");

		System.err.println(String.join("、", queue));
	}

	@AfterEach
	void after() {
		System.err.println(String.join("、", queue));
	}

	@Test
	void peekTest() {
		while (queue.peek() != null) {
			queue.remove();
		}
	}

}
