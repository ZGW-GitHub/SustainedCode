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

package com.code.java.thread.ooo.lock.util.semaphore;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

/**
 * @author Snow
 * @date 2022/11/28 21:33
 */
@Slf4j
public class DemoTest {

	@Test
	@SneakyThrows
	void simpleTest() {
		Semaphore semaphore = new Semaphore(1);

//		new Thread(() -> {
//			semaphore.release();
//		})

//		semaphore.release();
//		semaphore.release();
//		semaphore.release();
//		semaphore.release();

		semaphore.acquire();
		System.err.printf("2：%s \n", semaphore.availablePermits());
		semaphore.release();
//		System.err.printf("6：%s \n", semaphore.drainPermits());
		System.err.printf("4：%s \n", semaphore.availablePermits());


	}

}
