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
import com.code.jpa.dal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Snow
 * @date 2020/8/7 10:58 上午
 */
@Slf4j
public class AlterTest extends JpaApplicationTest {

	@Resource
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

}
