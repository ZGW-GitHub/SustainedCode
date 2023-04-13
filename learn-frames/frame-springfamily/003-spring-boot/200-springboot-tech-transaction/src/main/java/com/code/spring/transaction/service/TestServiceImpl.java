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

package com.code.spring.transaction.service;

import com.code.spring.transaction.dal.dos.User;
import com.code.spring.transaction.dal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Snow
 * @date 2022/5/5 21:33
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

	@Resource
	private UserRepository userRepository;

	@Override
	@Transactional
	public String test1() {
		userRepository.save(User.builder().name("test").age(16).build());

		// int i = 10 / 0;
		return ((TestService) AopContext.currentProxy()).test2();
	}

	@Override
	@Transactional
	public String test2() {
		log.info("test2 run ...");

		return "Test2";
	}

}
