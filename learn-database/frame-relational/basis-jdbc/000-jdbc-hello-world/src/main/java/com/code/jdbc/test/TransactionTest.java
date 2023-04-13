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

package com.code.jdbc.test;

import com.code.jdbc.JdbcUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Snow
 * @date 2022/1/25 4:19 PM
 */
@Slf4j
public class TransactionTest {

	private final Connection connection = JdbcUtil.CONNECTION_POOL.poll();

	@Test
	@SneakyThrows
	public void simpleTest() {
		connection.setAutoCommit(false); // 关闭事务的自动提交（ autoCommit 默认是 true ）

		// 1、获取执行 sql 的对象
		try (PreparedStatement statement = connection.prepareStatement(JdbcUtil.UPDATE_SQL)) {
			// 2、执行 sql ，接收返回结果
			statement.executeUpdate();

			// 3、提交/回滚
			connection.commit();
			log.info("commit over");
		} catch (Exception e) {
			// 3、提交/回滚
			connection.rollback();
			log.info("rollback over");
		}

		// 归还 Connection
		JdbcUtil.CONNECTION_POOL.add(connection);
	}

}
