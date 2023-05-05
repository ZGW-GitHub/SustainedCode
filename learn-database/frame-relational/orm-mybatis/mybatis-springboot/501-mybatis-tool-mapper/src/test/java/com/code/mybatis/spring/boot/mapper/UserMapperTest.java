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

package com.code.mybatis.spring.boot.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.code.mybatis.spring.boot.MybatisSpringBootApplicationTest;
import com.code.mybatis.spring.boot.dal.dos.User;
import com.code.mybatis.spring.boot.dal.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Snow
 * @date 2020/8/14 5:00 下午
 */
@Slf4j
public class UserMapperTest extends MybatisSpringBootApplicationTest {

	@Resource
	private UserMapper userMapper;

	@BeforeEach
	public void init() {
		ArrayList<User> userList = Lists.newArrayList();

		IntStream.range(1, 6).forEach(i -> {
			User user = User.builder().recordId(RandomUtil.randomLong(999999)).name("Mapper" + i).age(10)
					.createTime(new Date()).updateTime(new Date()).build();
			userList.add(user);
		});

		userMapper.insertList(userList);
	}

	@AfterEach
	public void destroy() {
		userMapper.deleteAll();
	}

	@Test
	void initTest() {
		log.info("init success !");
	}

	/**
	 * 测试通用Mapper - 保存
	 */
	@Test
	void insertTest() {
		User user = User.builder().recordId(RandomUtil.randomLong(999999)).name("Mapper").age(10)
				.createTime(new Date()).updateTime(new Date()).build();

		userMapper.insertUseGeneratedKeys(user);

		Assertions.assertNotNull(user.getId());

		log.info("【 测试主键回写：user.getId() 】= {}", user.getId());
	}

	/**
	 * 测试通用Mapper - 批量保存
	 */
	@Test
	void insertListTest() {
		List<User> userList = Lists.newArrayList();

		IntStream.range(2, 6).forEach(i -> {
			User user = User.builder().recordId(RandomUtil.randomLong(999999)).name("Mapper" + i).age(10)
					.createTime(new Date()).updateTime(new Date()).build();
			userList.add(user);
		});

		int i = userMapper.insertList(userList);

		Assertions.assertEquals(userList.size(), i);

		List<Integer> ids = userList.stream().map(User::getId).collect(Collectors.toList());

		log.info("【 测试主键回写：userList.ids 】= {}", ids);
	}

	/**
	 * 测试通用Mapper - 查询全部
	 */
	@Test
	void queryAllTest() {
		List<User> users = userMapper.selectAll();

		Assertions.assertTrue(CollUtil.isNotEmpty(users));

		users.forEach(System.err::println);
	}

}
