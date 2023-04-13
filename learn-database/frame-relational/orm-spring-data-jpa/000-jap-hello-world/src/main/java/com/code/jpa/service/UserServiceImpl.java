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

package com.code.jpa.service;

import com.code.jpa.dal.entity.User;
import com.code.jpa.dal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Snow
 * @date 2022/10/17 09:59
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserRepository userRepository;

	@Override
	public void demo(User user) {
		userRepository.save(user);
	}

	@Override
	public void saveCustom(User user) {
		userRepository.saveCustom(user.getName(), user.getAge());
	}

	@Override
	@Transactional
	public void transaction(User user) {
		userRepository.save(user);

		throw new ArithmeticException();
	}

}
