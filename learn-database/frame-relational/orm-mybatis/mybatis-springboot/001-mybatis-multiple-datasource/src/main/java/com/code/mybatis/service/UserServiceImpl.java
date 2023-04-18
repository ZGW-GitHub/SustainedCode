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

package com.code.mybatis.service;

import cn.hutool.core.util.RandomUtil;
import com.code.mybatis.dal.first.dos.FirstUser;
import com.code.mybatis.dal.first.mapper.FirstUserMapper;
import com.code.mybatis.dal.second.dos.SecondUser;
import com.code.mybatis.dal.second.mapper.SecondUserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author Snow
 * @date 2022/10/17 09:59
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private FirstUserMapper firstUserMapper;

	@Resource
	private SecondUserMapper secondUserMapper;

	@Resource(name = "firstDsTransactionManager")
	private DataSourceTransactionManager firstTransactionManager;

	@Resource(name = "secondDsTransactionManager")
	private DataSourceTransactionManager secondTransactionManager;

	@Override
	public void demo() {
		firstUserMapper.save(new FirstUser().setRecordId(RandomUtil.randomLong(999999)).setName("test").setAge(16));
		secondUserMapper.save(new SecondUser().setRecordId(RandomUtil.randomLong(999999)).setName("test").setAge(16));
	}

	@Override
	public void transactionByCode() {
		TransactionStatus status1 = firstTransactionManager.getTransaction(new DefaultTransactionDefinition());
		TransactionStatus status2 = secondTransactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			firstUserMapper.save(new FirstUser().setName("test").setAge(16));
			secondUserMapper.save(new SecondUser().setName("test").setAge(18));

			// log.info("触发异常：{}", 1 / 0);

			// 注意 commit 顺序：status1 先开启的并且配置的为可传播，则 status1 [最好]在 status2 之后 commit
			secondTransactionManager.commit(status2);
			firstTransactionManager.commit(status1);
		} catch (Exception e) {
			// 注意 rollback 顺序：status1 先开启的并且配置的为可传播，则 status1 [必须]在 status2 之后 rollback
			secondTransactionManager.rollback(status2);
			firstTransactionManager.rollback(status1);
		}
	}

	@Override
	@Transactional(transactionManager = "firstDsTransactionManager")
	public void transactionByAnno() {
		firstUserMapper.save(new FirstUser().setName("test").setAge(16));

		if (AopContext.currentProxy() instanceof UserServiceImpl userService) {
			userService.transactionByAnnoInner();
		}
	}

	@Transactional(transactionManager = "secondDsTransactionManager")
	public void transactionByAnnoInner() {
		secondUserMapper.save(new SecondUser().setName("test").setAge(18));

		// log.info("触发异常：{}", 1 / 0);
	}

}
