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

package collection.queue_basis.un_blocking.priority_queue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

/**
 * @author Snow
 * @date 2022/6/29 14:46
 */
@Slf4j
class DemoTest {

	final PriorityQueue<Integer> queue = new PriorityQueue<>();

	@BeforeEach
	void before() {
		queue.add(9);
		queue.add(7);
	}

	@Test
	void test() {
		System.err.println(queue.peek());
	}

	@AfterEach
	void after() {
		System.err.println(queue);
	}

}
