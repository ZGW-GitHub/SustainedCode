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

package collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * 有序性测试
 *
 * @author Snow
 * @date 2022/11/29 22:08
 */
@Slf4j
public class OrderlinessTest {

	/**
	 * 测试 list
	 */
	@Test
	void listTest() {
		listTest(new ArrayList<>());                // 放入顺序
		listTest(new LinkedList<>());               // 放入顺序
		listTest(new Vector<>());                   // 放入顺序
		listTest(new Stack<>());                    // 放入顺序
		listTest(new CopyOnWriteArrayList<>());     // 放入顺序
	}

	/**
	 * 测试 set
	 */
	@Test
	void setTest() {
		setTest(new LinkedHashSet<>());          // 放入顺序
		setTest(new CopyOnWriteArraySet<>());    // 放入顺序（并发）
		setTest(new TreeSet<>());                // 自动排序
		setTest(new ConcurrentSkipListSet<>());  // 自动排序（并发）
		setTest(new HashSet<>());                // 乱序
//		setTest(new EnumSet<>());                //
	}

	/**
	 * 测试 Map
	 */
	@Test
	void totalTest() {
		mapTest(new LinkedHashMap<>());                // 放入顺序
		mapTest(new TreeMap<>());                      // 自动排序
		mapTest(new ConcurrentSkipListMap<>());        // 自动排序（并发）
		mapTest(new HashMap<>());                      // 乱序
		mapTest(new Hashtable<>());                    // 乱序
		mapTest(new WeakHashMap<>());                  // 乱序
		mapTest(new IdentityHashMap<>());              // 乱序
		mapTest(new ConcurrentHashMap<>());            // 乱序（并发）
//		mapTest(new EnumMap<>()); 						//
	}

	/**
	 * 测试 queue
	 */
	@Test
	void queueTest() {
		queueTest(new PriorityQueue<>());                  // 优先级
		queueTest(new PriorityBlockingQueue<>());          // 优先级（并发）
		queueTest(new ArrayBlockingQueue<>(10));  // FIFO
		queueTest(new LinkedBlockingQueue<>());            // FIFO
		queueTest(new LinkedTransferQueue<>());            // FIFO
		queueTest(new ConcurrentLinkedQueue<>());          // FIFO
//		queueTest(new DelayQueue<>());                    // 延迟队列
//		queueTest(new SynchronousQueue<>());  //
	}

	/**
	 * 测试 deque
	 */
	@Test
	void dequeTest() {
		dequeTest(new ArrayDeque<>());             // 放入顺序
		dequeTest(new LinkedBlockingDeque<>());    // 放入顺序
		dequeTest(new ConcurrentLinkedDeque<>());  // 放入顺序
	}

	void listTest(List<String> list) {
		// 自动排序顺序：卡卡、小红、明明、杰克
		list.add("小红");
		list.add("明明");
		list.add("杰克");
		list.add("卡卡");

		System.err.println(list.getClass().getSimpleName() + " : " + list);
	}

	void setTest(Set<String> list) {
		// 自动排序顺序：卡卡、小红、明明、杰克
		list.add("小红");
		list.add("明明");
		list.add("杰克");
		list.add("卡卡");

		System.err.println(list.getClass().getSimpleName() + " : " + list);
	}

	void queueTest(Queue<String> queue) {
		// 自动排序顺序：卡卡、小红、明明、杰克
		queue.add("小红");
		queue.add("明明");
		queue.add("杰克");
		queue.add("卡卡");

		System.err.println(queue.getClass().getSimpleName() + " : " + queue);
	}

	void dequeTest(Queue<String> deque) {
		// 自动排序顺序：卡卡、小红、明明、杰克
		deque.add("小红");
		deque.add("明明");
		deque.add("杰克");
		deque.add("卡卡");

		System.err.println(deque.getClass().getSimpleName() + " : " + deque);
	}

	void mapTest(Map<String, String> map) {
		// 自动排序顺序：卡卡、小红、明明、杰克
		map.put("小红", "t");
		map.put("明明", "t");
		map.put("杰克", "t");
		map.put("卡卡", "t");

		System.err.println(map.getClass().getSimpleName() + " : " + map);
	}

}
