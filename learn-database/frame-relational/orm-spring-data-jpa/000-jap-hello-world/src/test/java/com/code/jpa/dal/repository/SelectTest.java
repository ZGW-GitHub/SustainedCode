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

package com.code.jpa.dal.repository;

import cn.hutool.core.util.StrUtil;
import com.code.jpa.JpaApplicationTest;
import com.code.jpa.dal.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Snow
 * @date 2020/8/7 10:58 上午
 */
@Slf4j
class SelectTest extends JpaApplicationTest {

	@Resource
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	void selectTest() {
		List<User> users = userRepository.findAll();

		Assertions.assertEquals(users.size(), 10);
	}

	/**
	 * 动态 SQL ：使用 别名 拼接参数
	 */
	@Test
	@SuppressWarnings("all")
	void sqlSelectTest1() {
		// 参数
		String        name     = "test";
		Boolean       isActive = Math.random() > 66 ? Math.random() > 66 : null;
		List<Integer> ages     = IntStream.range(3, 6).boxed().collect(Collectors.toList());

		// 创建查询SQL
		StringBuilder selectSql = new StringBuilder(" SELECT * FROM user ");
		StringBuilder countSql  = new StringBuilder(" SELECT COUNT(*) FROM user ");
		StringBuilder whereSql  = new StringBuilder(" WHERE 1 = 1 ");

		// 根据条件拼接 Where SQL
		if (StrUtil.isNotBlank(name)) {
			whereSql.append(" AND name LIKE '").append(name).append("%'");
		}
		if (!ages.isEmpty()) {
			whereSql.append(" AND age IN :age ");
		}
		if (isActive != null) {
			whereSql.append(" AND is_active = :isActive ");
		}

		// 拼接 Where
		selectSql.append(whereSql);
		countSql.append(whereSql);

		log.debug("Select Sql : " + selectSql.toString());
		log.debug("Count Sql : " + countSql.toString());

		// 创建本地 sql 查询实例
		Query dataQuery  = entityManager.createNativeQuery(selectSql.toString());
		Query countQuery = entityManager.createNativeQuery(countSql.toString());

		if (!ages.isEmpty()) {
			dataQuery.setParameter("age", ages);
			countQuery.setParameter("age", ages);
		}
		if (isActive != null) {
			dataQuery.setParameter("isActive", isActive);
			countQuery.setParameter("isActive", isActive);
		}

		// 获取查询结果
		List<Object> resultList = dataQuery.getResultList();
		BigInteger   count      = (BigInteger) countQuery.getSingleResult();

		log.error(Arrays.toString(resultList.toArray()));

		Assertions.assertEquals(count.intValue(), 3);
		Assertions.assertEquals(resultList.size(), 3);
	}

	/**
	 * 动态 SQL ：使用 位置编号 拼接参数
	 */
	@Test
	@SuppressWarnings("all")
	void sqlSelectTest2() {
		// 参数
		String             name     = "test";
		Boolean            isActive = Math.random() > 66 ? Math.random() > 66 : null;
		ArrayList<Integer> ages     = new ArrayList<>();
		ages.add(5);

		// 创建查询SQL
		StringBuilder selectSql = new StringBuilder("SELECT * FROM user");
		StringBuilder countSql  = new StringBuilder("SELECT COUNT(*) FROM user");
		StringBuilder whereSql  = new StringBuilder(" WHERE 1 = 1");

		// 根据条件拼接 Where SQL
		if (StrUtil.isNotBlank(name)) {
			whereSql.append(" AND name LIKE '").append(name).append("%'");
		}
		if (!ages.isEmpty()) {
			whereSql.append(" AND age IN ?1");
		}
		if (isActive != null) {
			whereSql.append(" AND is_active = ?2");
		}

		// 拼接 Where
		selectSql.append(whereSql);
		countSql.append(whereSql);

		log.debug("Select Sql : " + selectSql.toString());
		log.debug("Count Sql : " + countSql.toString());

		// 创建本地 sql 查询实例
		Query dataQuery  = entityManager.createNativeQuery(selectSql.toString());
		Query countQuery = entityManager.createNativeQuery(countSql.toString());

		if (!ages.isEmpty()) {
			dataQuery.setParameter(1, ages);
			countQuery.setParameter(1, ages);
		}
		if (isActive != null) {
			dataQuery.setParameter(2, isActive);
			countQuery.setParameter(2, isActive);
		}

		// 获取查询结果
		List<Object> resultList = dataQuery.getResultList();
		BigInteger   count      = (BigInteger) countQuery.getSingleResult();

		log.debug(Arrays.toString(resultList.toArray()));

		Assertions.assertEquals(count.intValue(), 1);
		Assertions.assertEquals(resultList.size(), 1);
	}

}
