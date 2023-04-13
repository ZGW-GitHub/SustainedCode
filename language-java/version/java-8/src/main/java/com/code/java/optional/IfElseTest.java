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

package com.code.java.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class IfElseTest {


	/**
	 * <pre>{@code
	 *     String name;
	 *     if(obj != null) {
	 *         name = obj.getName();
	 *     } else {
	 *         name = "default";
	 *     }
	 * }</pre>
	 * <p>如下所示，最好使用 map 。</p>
	 */
	@Test
	void ifElseTest() {
		Object test1 = Optional.ofNullable("null").map(str -> {
			return null;
		}).orElse("a");
		System.err.println(test1); // 结果正确

		Object test2 = Optional.ofNullable("null").flatMap(str -> {
			return null;
		}).orElse("a");
		System.err.println(test2); // 抛异常
	}


	/**
	 * 如下所示，可以一直 map
	 * <pre>{@code
	 *     String name;
	 *     if(obj != null) {
	 *         Object nameObj = obj.getNameObj();
	 *         if(nameObj != null) {
	 *             name = nameObj.getName();
	 *         } else {
	 * 	           name = "default";
	 *           }
	 *     } else {
	 *         name = "default";
	 *     }
	 * }</pre>
	 */
	@Test
	void nullConsumer() {
		Object test3 = Optional.ofNullable(null).map(str -> {
			return str + "-1";
		}).map(str -> {
			return str + "2";
		}).orElse("a");
		System.err.println(test3);
	}

}
