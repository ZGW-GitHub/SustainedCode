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

import cn.hutool.core.date.DateUtil;
import com.code.jdbc.JdbcUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Snow
 * @date 2022/11/15 21:50
 */
@Slf4j
public class BatchTest {

	private final Connection connection = JdbcUtil.CONNECTION_POOL.poll();

	@Test
	@SneakyThrows
	void simpleTest() {
		connection.setAutoCommit(false);

		// 1、获取执行 sql 的对象
		PreparedStatement statement = connection.prepareStatement(JdbcUtil.INSERT_SQL);
		try {
			for (int i = 1; i <= 20; i++) {
				statement.setInt(1, i);
				statement.setDate(2, DateUtil.date().toSqlDate());
				// 2、添加到 batch 中
				statement.addBatch();

				if (i % 6 == 0) {
					// 3、执行 sql ，接收返回结果
					statement.executeBatch();
					statement.clearBatch();
					log.info("executeBatch - " + i);
				}

				if (i == 13) {
//					throw new NumberFormatException();
				}

			}

			// 4、提交/回滚
			statement.executeBatch(); // 这一步不能少，防止遗漏数据
			statement.clearBatch();
			connection.commit();
			log.info("commit over");
		} catch (Exception e) {
			// 4、提交/回滚
			connection.rollback(); // 这里会将 已 executeBatch() 、未 executeBatch() 的都回滚
			log.info("rollback over");
		} finally {
			if (statement != null) {
				// 4、关闭资源
				statement.close();
			}
		}

		// 归还 Connection
		JdbcUtil.CONNECTION_POOL.add(connection);
	}

}
