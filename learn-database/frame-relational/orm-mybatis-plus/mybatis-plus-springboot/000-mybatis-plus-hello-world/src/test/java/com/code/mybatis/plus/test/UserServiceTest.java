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

package com.code.mybatis.plus.test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.mybatis.plus.MybatisPlusApplicationTest;
import com.code.mybatis.plus.dal.dos.User;
import com.code.mybatis.plus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Snow
 * @date 2020/8/14 6:44 下午
 */
@Slf4j
class UserServiceTest extends MybatisPlusApplicationTest {

	@Autowired
	private UserService userService;

	/**
	 * 测试 Mybatis-Plus 新增
	 */
	@Test
	void saveTest() {
		User user = new User().setRecordId(RandomUtil.randomLong(Long.MAX_VALUE)).setName(RandomUtil.randomString(10)).setAge(10);

		boolean save = userService.save(user);

		Assertions.assertTrue(save);

		log.debug("【 测试 id 回显 #user.getId() 】= {}", user.getId());
	}

	/**
	 * 测试 Mybatis-Plus 批量新增
	 */
	@Test
	void testSaveList() {
		List<User> userList = Lists.newArrayList();

		for (int i = 4; i < 14; i++) {
			User user = new User().setRecordId(RandomUtil.randomLong(Long.MAX_VALUE)).setName(RandomUtil.randomString(10)).setAge(10);

			userList.add(user);
		}

		boolean batch = userService.saveBatch(userList);
		Assertions.assertTrue(batch);

		List<Integer> ids = userList.stream().map(User::getId).collect(Collectors.toList());

		log.debug("【 userList#ids 】= {}", ids);
	}

	/**
	 * 测试 Mybatis-Plus 删除
	 */
	@Test
	void testDelete() {
		boolean remove = userService.removeById(6L);

		Assertions.assertTrue(remove);

		User byId = userService.getById(6L);

		Assertions.assertNull(byId);
	}

	/**
	 * 测试 Mybatis-Plus 修改
	 */
	@Test
	void testUpdate() {
		User user = userService.getById(2L);

		Assertions.assertNotNull(user);

		user.setName("修改名字");

		boolean bool = userService.updateById(user);
		Assertions.assertTrue(bool);

		User update = userService.getById(2L);
		Assertions.assertEquals("修改名字", update.getName());

		log.debug("【 update 】= {}", update);
	}

	/**
	 * 测试 Mybatis-Plus 查询单个
	 */
	@Test
	void testQueryOne() {
		User user = userService.getById(2L);

		Assertions.assertNotNull(user);

		log.debug("【 user 】= {}", user);
	}

	/**
	 * 测试 Mybatis-Plus 查询全部
	 */
	@Test
	void testQueryAll() {
		List<User> list = userService.list(new QueryWrapper<>());

		Assertions.assertTrue(CollUtil.isNotEmpty(list));

		log.debug("【 list 】= {}", list);
	}

	/**
	 * 测试 Mybatis-Plus 分页排序查询
	 */
	@Test
	void testQueryByPageAndSort() {
		long count = userService.count(new QueryWrapper<>());

		Page<User> userPage = new Page<>(1, 5);

		userPage.setOrders(Collections.singletonList(OrderItem.desc("id")));

		IPage<User> page = userService.page(userPage, new QueryWrapper<>());

		Assertions.assertEquals(5, page.getSize());
		Assertions.assertEquals(count, page.getTotal());

		log.debug("【 page.getRecords() 】= {}", page.getRecords());
	}

	/**
	 * 测试 Mybatis-Plus 自定义查询
	 */
	@Test
	void testQueryByCondition() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.like("name", "test").or().eq("age", "2").orderByDesc("id");

		long       count    = userService.count(wrapper);
		Page<User> userPage = new Page<>(1, 3);

		IPage<User> page = userService.page(userPage, wrapper);

		Assertions.assertEquals(3, page.getSize());
		Assertions.assertEquals(count, page.getTotal());

		log.debug("【 page.getRecords() 】= {}", page.getRecords());
	}

}
