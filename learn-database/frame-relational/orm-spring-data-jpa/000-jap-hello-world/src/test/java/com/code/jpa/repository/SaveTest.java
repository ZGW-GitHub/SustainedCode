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

package com.code.jpa.repository;

import com.code.jpa.basic.JpaApplicationTest;
import com.code.jpa.dal.entity.User;
import com.code.jpa.dal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author Snow
 * @date 2020/8/7 10:58 上午
 */
@Slf4j
class SaveTest extends JpaApplicationTest {

	@Resource
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	@Rollback(value = false)
	void repositoryTest() {
		List<User> users = LongStream.rangeClosed(1, 10)
									 .boxed()
									 .map(i -> new User().setName("test" + i).setAge(i.intValue()))
									 .collect(Collectors.toList());

		userRepository.saveAll(users);
	}

	@Test
	@Transactional
	@Rollback(value = false)
	void entityManagerTest() {
		List<User> users = LongStream.rangeClosed(1, 10)
									.boxed()
									.map(i -> new User().setName("test" + i).setAge(i.intValue()))
									.toList();

		int i = 0;
		for (User user : users) {
			entityManager.persist(user);
			i++;
			if (i % 2 == 0) {
				entityManager.flush();
				entityManager.clear();
			}

			System.err.println("test");
		}
	}

}
