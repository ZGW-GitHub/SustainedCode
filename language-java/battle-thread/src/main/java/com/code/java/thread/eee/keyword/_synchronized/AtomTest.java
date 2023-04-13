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

package com.code.java.thread.eee.keyword._synchronized;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/12/3 11:15
 */
@Slf4j
public class AtomTest {

	int count = 1;

	@Test
	@SneakyThrows
	void test() {
		new Thread(() -> {
			synchronized (AtomTest.class) {
				count = 12;
				throw new NumberFormatException();
			}
		}).start();

		TimeUnit.SECONDS.sleep(1);

		new Thread(() -> {
			synchronized (AtomTest.class) {
				System.err.println(count);
			}
		}).start();

		TimeUnit.SECONDS.sleep(2);
	}

}
