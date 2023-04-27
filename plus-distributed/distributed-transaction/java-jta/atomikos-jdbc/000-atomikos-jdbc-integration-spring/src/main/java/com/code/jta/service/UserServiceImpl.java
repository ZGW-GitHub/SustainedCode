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

package com.code.jta.service;

import cn.hutool.core.util.RandomUtil;
import com.code.jta.dal.first.dos.FirstUser;
import com.code.jta.dal.first.mapper.FirstUserMapper;
import com.code.jta.dal.second.dos.SecondUser;
import com.code.jta.dal.second.mapper.SecondUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

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

	@Resource
	private PlatformTransactionManager transactionManager;

	@Override
	public void demo() {
		firstUserMapper.save(new FirstUser().setRecordId(RandomUtil.randomLong(999999)).setName("test").setAge(16));
		secondUserMapper.save(new SecondUser().setRecordId(RandomUtil.randomLong(999999)).setName("test").setAge(16));
	}

	@Override
	public void transactionByCode() {
		TransactionStatus status1 = transactionManager.getTransaction(new DefaultTransactionDefinition());
		TransactionStatus status2 = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			firstUserMapper.save(new FirstUser().setRecordId(RandomUtil.randomLong()).setName("test").setAge(16));
			secondUserMapper.save(new SecondUser().setRecordId(RandomUtil.randomLong()).setName("test").setAge(18));

			// log.info("触发异常：{}", 1 / 0);

			// 注意 commit 顺序：status1 先开启的并且配置的为可传播，则 status1 [最好]在 status2 之后 commit
			transactionManager.commit(status2);
			transactionManager.commit(status1);
		} catch (Exception e) {
			log.error("保存发生异常：{}", e.getMessage(), e);
			// 注意 rollback 顺序：status1 先开启的并且配置的为可传播，则 status1 [必须]在 status2 之后 rollback
			transactionManager.rollback(status2);
			transactionManager.rollback(status1);
		}
	}

	@Override
	@Transactional
	public void transactionByAnno() {
		firstUserMapper.save(new FirstUser().setName("test").setAge(16));

		if (AopContext.currentProxy() instanceof UserServiceImpl userService) {
			userService.transactionByAnnoInner();
		}
	}

	@Transactional
	public void transactionByAnnoInner() {
		secondUserMapper.save(new SecondUser().setRecordId(RandomUtil.randomLong()).setName("test").setAge(18));

		// log.info("触发异常：{}", 1 / 0);
	}

}
