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

import com.code.mybatis.spring.boot.MybatisApplicationTest;
import com.code.mybatis.spring.boot.dal.dos.User;
import com.code.mybatis.spring.boot.dal.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Snow
 * @date 2023/4/7 11:09
 */
@Slf4j
public class SelectTest extends MybatisApplicationTest {

	@Resource
	private UserMapper userMapper;

	@Test
	void demo() {
		List<User> userList = userMapper.listAll();
		userList = Optional.ofNullable(userList).orElse(Collections.emptyList());

		userList.forEach(System.err::println);
	}

}
