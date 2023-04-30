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

package com.code.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Snow
 * @date 2022/1/25 5:17 PM
 */
public class JdbcUtil {

	private static final String URL      = "jdbc:mysql://linux.little:63307/frame_relational";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	public static final String SELECT_SQL = "select * from user where 1 = ?";
	public static final String INSERT_SQL = "insert into user(age, create_time) values(?, ?)";
	public static final String UPDATE_SQL = "update user set create_time = NOW() where 1 = 1";

	public static final LinkedBlockingQueue<Connection> CONNECTION_POOL = new LinkedBlockingQueue<>(10);

	static {
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			CONNECTION_POOL.add(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
