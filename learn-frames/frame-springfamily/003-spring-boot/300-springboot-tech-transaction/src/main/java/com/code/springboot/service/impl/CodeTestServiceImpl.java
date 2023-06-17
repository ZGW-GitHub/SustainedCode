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

package com.code.springboot.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.code.springboot.dal.dos.User;
import com.code.springboot.dal.mapper.UserMapper;
import com.code.springboot.service.CodeTestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Snow
 * @date 2023/6/15 13:17
 */
@Slf4j
@Service
public class CodeTestServiceImpl implements CodeTestService {

	@Resource
	private PlatformTransactionManager transactionManager;

	@Resource
	private UserMapper userMapper;

	@Override
	public void demo() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition(); // 可以在这里配置事务的隔离性、传播性等

		TransactionStatus status = transactionManager.getTransaction(definition);

		try {
			System.err.println("1 : " + TransactionSynchronizationManager.isActualTransactionActive());

			userMapper.save(new User().setRecordId(RandomUtil.randomLong()).setName("code-1").setAge(16));
			// int i = 1/0;
			userMapper.save(new User().setRecordId(RandomUtil.randomLong()).setName("code-1").setAge(16));

			System.err.println("2 : " + TransactionSynchronizationManager.isActualTransactionActive());
		} catch (Exception e) {
			log.error("发生异常：{}", e.getMessage(), e);

			transactionManager.rollback(status);

			System.err.println("3 : " + TransactionSynchronizationManager.isActualTransactionActive());
		} finally {
			if (TransactionSynchronizationManager.isActualTransactionActive()) {
				log.debug("提交事务");
				transactionManager.commit(status);
			}
		}
	}

}
