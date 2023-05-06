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

package com.code.spring.boot.component.transaction;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronizationUtils;

/**
 * @author Snow
 * @date 2023/5/6 11:40
 */
public class DynamicDataSourceTransactionObject extends JdbcTransactionObjectSupport {

	private boolean newConnectionHolder;

	private boolean mustRestoreAutoCommit;

	public void setConnectionHolder(@Nullable ConnectionHolder connectionHolder, boolean newConnectionHolder) {
		super.setConnectionHolder(connectionHolder);
		this.newConnectionHolder = newConnectionHolder;
	}

	public boolean isNewConnectionHolder() {
		return this.newConnectionHolder;
	}

	public void setMustRestoreAutoCommit(boolean mustRestoreAutoCommit) {
		this.mustRestoreAutoCommit = mustRestoreAutoCommit;
	}

	public boolean isMustRestoreAutoCommit() {
		return this.mustRestoreAutoCommit;
	}

	public void setRollbackOnly() {
		getConnectionHolder().setRollbackOnly();
	}

	@Override
	public boolean isRollbackOnly() {
		return getConnectionHolder().isRollbackOnly();
	}

	@Override
	public void flush() {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			TransactionSynchronizationUtils.triggerFlush();
		}
	}
}