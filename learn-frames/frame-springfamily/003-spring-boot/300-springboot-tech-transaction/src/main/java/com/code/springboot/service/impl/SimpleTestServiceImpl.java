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
import com.code.springboot.service.SimpleTestService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/5/5 21:33
 */
@Slf4j
@Service
public class SimpleTestServiceImpl implements SimpleTestService {

	@Resource
	private UserMapper userMapper;

	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public void test1() {
		userMapper.save(new User().setRecordId(RandomUtil.randomLong()).setName("tran-1").setAge(16));

		TimeUnit.SECONDS.sleep(1);

		// 通过下面两种不同的调用可以发现只有 ② 的调用方式 test2 方法的 @Transaction 才会生效
		// this.test2(); // ①
		((SimpleTestService) AopContext.currentProxy()).test2(); // ②

		// throw new ArithmeticException();
	}

	@Override
	@SneakyThrows
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void test2() {
		log.info("test2 run ...");
		TimeUnit.SECONDS.sleep(1);

		userMapper.save(new User().setRecordId(RandomUtil.randomLong()).setName("tran-2").setAge(16));
	}

}
