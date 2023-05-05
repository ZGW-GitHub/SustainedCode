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

package com.code.spring.boot.component.datasource;

import com.code.spring.boot.component.transaction.MultiTransactionManager;
import com.code.spring.boot.component.transaction.TransactionHolder;
import jakarta.validation.constraints.NotNull;
import org.springframework.jdbc.datasource.ConnectionProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Snow
 * @date 2023/4/16 20:37
 */
public class SnowDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
	}

	@NotNull
	@Override
	public Connection getConnection() throws SQLException {
		TransactionHolder transactionHolder = MultiTransactionManager.TRANSACTION_HOLDER_THREAD_LOCAL.get();
		if (Objects.isNull(transactionHolder)) {
			return determineTargetDataSource().getConnection();
		}
		ConnectionProxy ConnectionProxy = transactionHolder.getConnectionMap()
				.get(transactionHolder.getExecuteStack().peek());
		if (ConnectionProxy == null) {
			// 没开跨库事务，直接返回
			return determineTargetDataSource().getConnection();
		} else {
			transactionHolder.addCount();
			// 开了跨库事务，从当前线程中拿包装过的Connection
			return ConnectionProxy;
		}
	}
}
