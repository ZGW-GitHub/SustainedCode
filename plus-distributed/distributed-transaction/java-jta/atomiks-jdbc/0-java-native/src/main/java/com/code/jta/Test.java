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

package com.code.jta;

import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;
import lombok.extern.slf4j.Slf4j;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Snow
 * @date 2022/7/21 22:58
 */
@Slf4j
public class Test {
	public static void main(String[] args) throws SQLException {

		// true 表示打印 XA 语句, 用于调试
		boolean logXaCommands = true;

		// 获得资源管理器操作接口实例 RM1
		Connection   conn1   = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
		XAConnection xaConn1 = new MysqlXAConnection((com.mysql.jdbc.Connection) conn1, logXaCommands);
		XAResource   rm1     = xaConn1.getXAResource();
		// 获得资源管理器操作接口实例 RM2
		Connection   conn2   = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
		XAConnection xaConn2 = new MysqlXAConnection((com.mysql.jdbc.Connection) conn2, logXaCommands);
		XAResource   rm2     = xaConn2.getXAResource();

		// AP（应用程序）请求 TM（事务管理器） 执行一个分布式事务, TM 生成全局事务 ID
		byte[] gtrId    = "distributed_transaction_id_1".getBytes();
		int    formatId = 1;
		try {
			// ============== 分别执行 RM1 和 RM2 上的事务分支 ====================
			// TM 生成 RM1 上的事务分支 ID
			byte[] bqual1 = "transaction_001".getBytes();
			Xid    xid1   = new MysqlXid(gtrId, bqual1, formatId);
			// 执行 RM1 上的事务分支
			rm1.start(xid1, XAResource.TMNOFLAGS);
			PreparedStatement ps1 = conn1.prepareStatement("INSERT into user(record_id, name) VALUES (1008601, 'jack')");
			ps1.execute();
			rm1.end(xid1, XAResource.TMSUCCESS);

			// TM 生成 RM2 上的事务分支 ID
			byte[] bqual2 = "transaction_002".getBytes();
			Xid    xid2   = new MysqlXid(gtrId, bqual2, formatId);
			// 执行 RM2 上的事务分支
			rm2.start(xid2, XAResource.TMNOFLAGS);
			PreparedStatement ps2 = conn2.prepareStatement("INSERT into user(record_id, name) VALUES (1008602, 'rose')");
			ps2.execute();
			rm2.end(xid2, XAResource.TMSUCCESS);

			// =================== 两阶段提交 ================================
			// phase1: 询问所有的RM 准备提交事务分支
			int rm1Prepare = rm1.prepare(xid1);
			int rm2Prepare = rm2.prepare(xid2);

			// phase2: 提交所有事务分支
			if (rm1Prepare == XAResource.XA_OK && rm2Prepare == XAResource.XA_OK) {
				// 所有事务分支都 prepare 成功, 提交所有事务分支
				rm1.commit(xid1, false);
				rm2.commit(xid2, false);
			} else {
				// 如果有事务分支没有成功, 则回滚
				rm1.rollback(xid1);
				rm2.rollback(xid2);
			}
		} catch (XAException | SQLException e) {
			e.printStackTrace();
		}

	}
}
