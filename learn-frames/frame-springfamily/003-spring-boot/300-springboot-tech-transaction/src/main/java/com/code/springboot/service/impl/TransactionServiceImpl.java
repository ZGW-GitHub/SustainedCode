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

package com.code.springboot.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.code.springboot.dal.dos.User;
import com.code.springboot.dal.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Snow
 * @date 2023/6/10 20:22
 */
@Slf4j
@Service
public class TransactionServiceImpl {

	@Resource
	private UserMapper userMapper;

	@Transactional
	public void transaction() {
		userMapper.save(new User().setRecordId(RandomUtil.randomLong()).setName("scheduled-").setAge(16));

		int num = 1 / 0;

		userMapper.save(new User().setRecordId(RandomUtil.randomLong()).setName("scheduled2").setAge(16));
	}

}
