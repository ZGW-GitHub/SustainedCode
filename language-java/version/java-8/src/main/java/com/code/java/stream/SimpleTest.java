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

package com.code.java.stream;

import com.code.java.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Snow
 * @date 2020/7/15 4:05 下午
 */
public class SimpleTest {

	private static final List<User> USER_LIST = new ArrayList<>();

	@BeforeEach
	public void initData() {
		USER_LIST.add(new User(1, "test2"));
		USER_LIST.add(new User(2, "test2"));
		USER_LIST.add(new User(3, "test5"));
		USER_LIST.add(new User(4, "test3"));
		USER_LIST.add(new User(2, "test1"));
		USER_LIST.add(new User(1, "test1"));
	}

	@AfterEach
	public void destroy() {
		System.out.println("\n\nTest Over !");
	}

	/**
	 * 测试排序、截取
	 */
	@Test
	void sortTest() {
		List<User> collect = USER_LIST.stream().sorted(Comparator.comparing(User::getAge).thenComparing(User::getName)).collect(Collectors.toList());

		collect.forEach(user -> System.out.println(user.getAge() + "——" + user.getName()));

		System.out.println("---");

		collect = collect.subList(4, collect.size());
		collect.forEach(user -> System.out.println(user.getAge() + "——" + user.getName()));
		System.out.println(collect.size());
	}

	/**
	 * 测试分组
	 */
	@Test
	void groupByTest() {
		Map<Integer, Map<Integer, String>> userMap = USER_LIST.stream()
				.collect(Collectors.groupingBy(User::getAge,
						Collectors.toMap(User::getAge, User::getName, (v1, v2) -> v2)));

		userMap.forEach((k, v) -> {
			System.out.println(k);

			v.forEach((k1, v1) -> {
				System.out.println("-- " + k1);
				System.out.println("---- " + v1);
			});
		});

		System.out.println("---------------------------");

		Map<Integer, List<String>> userMapList = USER_LIST.stream()
														  .collect(Collectors.groupingBy(User::getAge, Collectors.mapping(User::getName, Collectors.toList())));

		userMapList.forEach((k, v) -> {
			System.out.println(k);
			System.out.println(Arrays.toString(v.toArray()));
		});

		System.out.println("---------------------------");

		Map<Integer, Set<String>> userMapSet = USER_LIST.stream()
														.collect(Collectors.groupingBy(User::getAge, Collectors.mapping(User::getName, Collectors.toSet())));

		userMapSet.forEach((k, v) -> {
			System.out.println(k);
			System.out.println(Arrays.toString(v.toArray()));
		});
	}

	@Test
	void skipAndLimitTest() {
		USER_LIST.stream().skip(2).forEach(u -> System.out.println(u.getAge()));
		System.out.println("---");
		USER_LIST.stream().limit(2).forEach(u -> System.out.println(u.getAge()));
	}

	@Test
	void numberStreamTest() throws InterruptedException {
		IntStream.rangeClosed(1, 0).forEach(System.err::println);

		TimeUnit.SECONDS.sleep(2);
	}

	/**
	 * map 是对流元素进行转换。
	 * flatMap 是对流中的元素进行平铺后合并，即：对流中的每个元素平铺后又转换成为了 Stream 流。
	 */
	@Test
	void mapVsFlatMapTest() throws InterruptedException {
		String[] arr1 = {"a", "b", "c"};
		String[] arr2 = {"d", "e", "f"};
		String[] arr3 = {"g", "h"};

		Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.err::println);

		TimeUnit.SECONDS.sleep(1);
	}

	/**
	 * reduce：归约，将多个值变成一个值
	 */
	@Test
	void reduceTest() {
		List<Integer> ageList = USER_LIST.stream().map(User::getAge).collect(Collectors.toList());

		// 求和
		Optional<Integer> ageTotal = ageList.stream().reduce(Integer::sum);
		System.err.println(ageTotal);

		// 求积
		Optional<Integer> ageProduct = ageList.stream().reduce((x, y) -> x * y);
		System.err.println(ageProduct);

		// 求最大值
		Optional<Integer> ageMax = ageList.stream().reduce(Integer::max);
		System.err.println(ageMax);
	}

	/**
	 * 相较于 reduce ，增加了对自定义归约的支持
	 */
	@Test
	void reducingTest() {
		Integer ageTotal = USER_LIST.stream().map(User::getAge).reduce(0, Integer::sum);

		System.err.println(ageTotal);
	}

}
