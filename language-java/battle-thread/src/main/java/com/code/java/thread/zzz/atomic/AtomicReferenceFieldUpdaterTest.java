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

package com.code.java.thread.zzz.atomic;


import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author Snow
 */
public class AtomicReferenceFieldUpdaterTest {

	@Test
	void test() {
		AtomicReferenceFieldUpdater<User, Integer> fieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, Integer.class, "age");

		User user = User.builder().name("test").age(10).build();

		fieldUpdater.set(user, 4);

		Integer get = fieldUpdater.getAndSet(user, 3);
		System.out.println(get);

		System.out.println(user);
	}

}
