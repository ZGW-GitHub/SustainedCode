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

package com.code.mybatis.mapper;

import cn.hutool.core.util.RandomUtil;
import com.code.mybatis.MybatisApplicationTest;
import com.code.mybatis.dal.dos.User;
import com.code.mybatis.dal.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Snow
 * @date 2022/7/4 23:30
 */
@Slf4j
class SaveTest extends MybatisApplicationTest {

	@Resource
	private UserMapper userMapper;

	@Test
	@Transactional
	@Rollback(value = false)
	void simpleTest() {
		User user = new User()
				.setRecordId(RandomUtil.randomLong(999999))
				.setName(RandomUtil.randomString(10))
				.setAge(18);

		userMapper.save(user);
	}

}
