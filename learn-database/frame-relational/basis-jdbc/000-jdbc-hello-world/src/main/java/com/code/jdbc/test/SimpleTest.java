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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Snow
 * @date 2022/11/15 21:04
 */
@Slf4j
public class SimpleTest {

	private final Connection connection = JdbcUtil.CONNECTION_POOL.poll();

	@Test
	void simpleTest() throws SQLException {
		// 1、获取执行 sql 的对象
		PreparedStatement statement = connection.prepareStatement(JdbcUtil.SELECT_SQL);
		statement.setInt(1, 1);

		// 2、执行 sql ，接收返回结果
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			System.err.println(result.getString("name"));
		}

		// 3、关闭资源
		statement.close();
		result.close();

		// 归还 Connection
		JdbcUtil.CONNECTION_POOL.add(connection);
	}

}
