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

package com.code.elasticsearch.dal.repository;

import com.code.elasticsearch.ElasticsearchApplicationTest;
import com.code.elasticsearch.dal.dos.User;
import com.code.elasticsearch.util.GenerateUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;

/**
 * @author Snow
 * @date 2022/6/14 17:12
 */
@Slf4j
public class UserRepositoryTest extends ElasticsearchApplicationTest {

	@Resource
	private UserRepository userRepository;

	@Test
	void saveTest() {
		userRepository.save(GenerateUserUtil.generate());
	}

	@Test
	void deleteTest() {
		userRepository.deleteById("003037");
	}

	@Test
	void selectTest() {
		Page<User> userPage = userRepository.findAll(Pageable.ofSize(10));

		userPage.getContent().stream().map(User::toString).forEach(System.err::println);
	}

	/**
	 * TODO 搜索测试
	 */
	@Test
	void searchTest() {
		User user = new User().name("%1%");

		Page<User> userPage = userRepository.searchSimilar(user, new String[]{"name"}, Pageable.ofSize(10));

		userPage.getContent().stream().map(User::toString).forEach(System.err::println);
	}

}
