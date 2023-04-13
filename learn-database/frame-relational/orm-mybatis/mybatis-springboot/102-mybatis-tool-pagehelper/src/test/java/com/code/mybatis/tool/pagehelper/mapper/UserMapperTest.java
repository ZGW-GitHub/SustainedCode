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

package com.code.mybatis.tool.pagehelper.mapper;

import com.code.mybatis.tool.pagehelper.MybatisToolPagehelperApplicationTest;
import com.code.mybatis.tool.pagehelper.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Snow
 * @date 2020/8/14 5:00 下午
 */
@Slf4j
public class UserMapperTest extends MybatisToolPagehelperApplicationTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	void initTest() {
		log.info("init success !");
	}

	@Test
	void deleteAll() {
		userMapper.deleteAll();
	}

	/**
	 * 测试分页助手 - 分页排序查询
	 */
	@Test
	void queryByPageAndSortTest() {
		int    currentPage = 1;
		int    pageSize    = 5;
		String orderBy     = "id desc";

		// 分页
		PageHelper.startPage(currentPage, pageSize, orderBy);
		// 查询
		List<User>     users        = userMapper.findAll();
		PageInfo<User> userPageInfo = new PageInfo<>(users);

		Assertions.assertEquals(5, userPageInfo.getSize());

		log.info("【 userPageInfo 】= {}", userPageInfo);
	}

}
