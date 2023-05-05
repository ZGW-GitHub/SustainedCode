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

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.ConnectionProxy;
import org.springframework.transaction.annotation.Isolation;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Snow
 * @date 2023/5/5 21:27
 */
@Slf4j
public class TransactionHolder {

	/**
	 * 是否开启了一个 MultiTransaction
	 */
	private boolean       isOpen;
	/**
	 * 是否只读事务
	 */
	private boolean       readOnly;
	/**
	 * 事务隔离级别
	 */
	private Isolation     Isolation;
	/**
	 * 事务执行栈
	 */
	private Stack<String> executeStack;
	/**
	 * 数据源切换栈
	 */
	private Stack<String> datasourceKeyStack;
	/**
	 * 主事务ID
	 */
	private String        mainTransactionId;
	/**
	 * 执行次数
	 */
	private AtomicInteger transCount;

	/**
	 * 维护当前线程事务 ID 连接关系
	 */
	private ConcurrentHashMap<String, ConnectionProxy> connectionMap;

	/**
	 * 事务和数据源 key 关系
	 */
	private ConcurrentHashMap<String, String> executeIdDatasourceKeyMap;

}
