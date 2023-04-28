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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.code.mybatis.plus.MybatisPlusApplicationTest;
import com.code.mybatis.plus.dal.dos.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Snow
 * @date 2020/8/14 6:43 下午
 */
@Slf4j
class UserEntityTest extends MybatisPlusApplicationTest {

	/**
	 * 测试插入数据
	 */
	@Test
	void testActiveRecordInsert() {
		User user = new User().setId(2).selectById();

		user.setName("VIP").setAge(12);

		Assertions.assertTrue(user.insert());

		// 成功直接拿回写的 ID
		log.debug("【 User 】= {}", user);
	}

	/**
	 * 测试更新数据
	 */
	@Test
	void testActiveRecordUpdate() {
		// 根据id更新
		Assertions.assertTrue(
				new User().setId(2).setName("管理员-1").updateById()
		);

		// 根据 eq 函数中的属性值更新
		Assertions.assertTrue(
				new User().update(new UpdateWrapper<User>().lambda().set(User::getName, "普通用户-1").eq(User::getId, 3))
		);
	}

	/**
	 * 测试查询数据
	 */
	@Test
	void testActiveRecordSelect() {
		Assertions.assertEquals("普通用户-1", new User().setId(2).selectById().getName());

		User user = new User().selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 2));
		Assertions.assertEquals("普通用户-1", user.getName());

		List<User> Users = new User().selectAll();
		Assertions.assertTrue(Users.size() > 0);

		log.debug("【 Users 】= {}", Users);
	}

	/**
	 * 测试删除数据
	 */
	@Test
	void testActiveRecordDelete() {
		Assertions.assertTrue(new User().setId(23).deleteById());

		Assertions.assertTrue(new User().delete(new QueryWrapper<User>().lambda().eq(User::getName, "test")));
	}

}
