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

package com.code.spring.boot.component.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Snow
 * @date 2023/5/6 17:34
 */
public class ConnectionContextHolder {

	private static final ThreadLocal<Map<String, Connection>> CONNECTION_HOLDER = ThreadLocal.withInitial(() -> new ConcurrentHashMap<>(8));

	public static void putConnection(String ds, Connection connection) {
		Map<String, Connection> concurrentHashMap = CONNECTION_HOLDER.get();
		if (!concurrentHashMap.containsKey(ds)) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			concurrentHashMap.put(ds, connection);
		}
	}

	public static Connection getConnection(String ds) {
		return CONNECTION_HOLDER.get().get(ds);
	}

}
