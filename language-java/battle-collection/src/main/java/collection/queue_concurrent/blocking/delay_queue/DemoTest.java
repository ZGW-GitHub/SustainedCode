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

package collection.queue_concurrent.blocking.delay_queue;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.DelayQueue;

/**
 * 无界
 *
 * @author Snow
 */
class DemoTest {

	final DelayQueue<DelayQueueEntry> queue = new DelayQueue<>();

	@BeforeEach
	void before() {
		queue.add(new DelayQueueEntry("one", 5));
		queue.add(new DelayQueueEntry("two", 2));

		System.err.println(queue);
	}

	@Test
	@SneakyThrows
	void test() {
		int size = queue.size();
		for (int i = 1; i <= size; i++) {
			System.err.println(queue.take());
		}
	}

	@AfterEach
	void after() {
		System.err.println(queue);
	}

}

