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

package com.code.mybatis.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.code.mybatis.dal.first.dos.FirstUser;
import com.code.mybatis.dal.first.mapper.FirstUserMapper;
import com.code.mybatis.dal.second.dos.SecondUser;
import com.code.mybatis.dal.second.mapper.SecondUserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Snow
 * @date 2022/10/17 09:59
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private FirstUserMapper firstUserMapper;

	@Resource
	private SecondUserMapper secondUserMapper;

	@Override
	public void demo() {
		firstUserMapper.save(new FirstUser().setRecordId(RandomUtil.randomLong(999999)).setName("test").setAge(16));
		secondUserMapper.save(new SecondUser().setRecordId(RandomUtil.randomLong(999999)).setName("test").setAge(16));
	}

	@DS("first")
	@Override
	@Transactional
	public void transactionByAnno() {
		firstUserMapper.save(new FirstUser().setRecordId(RandomUtil.randomLong(999999)).setName("test").setAge(16));

		if (AopContext.currentProxy() instanceof UserServiceImpl userService) {
			userService.transactionByAnnoInner();
		}
	}

	@DS("second")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void transactionByAnnoInner() {
		secondUserMapper.save(new SecondUser().setRecordId(RandomUtil.randomLong(999999)).setName("test").setAge(18));

		// log.info("触发异常：{}", 1 / 0);
	}

}
