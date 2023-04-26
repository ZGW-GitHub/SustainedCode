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

package com.code.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.mybatis.plus.dal.dos.User;
import com.code.mybatis.plus.dal.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @author Snow
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private SqlSessionFactory sqlSessionFactory;

	@Override
	@Transactional
	public void transaction(User user) {
		userMapper.insert(user);

		throw new ArithmeticException();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchSaveByMybatis() {
		List<User> userList = LongStream.rangeClosed(1, 20).boxed()
				.map(i -> {
					if (i == 17) {
						return new User().setName("这里名字超出限制，在保存时会发生异常").setAge(i.intValue());
					}
					return new User().setName("test" + i).setAge(i.intValue());
				}).toList();

		this.saveBatch(userList, 6);
	}

	/**
	 * 摘抄自 MyBatis 批量操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchSaveByCustom() {
		List<User> userList = LongStream.rangeClosed(1, 20).boxed()
				.map(i -> {
					if (i == 17) {
						return new User().setName("这里名字超出限制，在保存时会发生异常").setAge(i.intValue());
					}
					return new User().setName("test" + i).setAge(i.intValue());
				}).toList();

		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try (sqlSession) {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class); // 必须通过 SqlSession 获取，不能用注入的
			int        index      = 1;
			for (User user : userList) {
				userMapper.insert(user);

				if (index % 6 == 0) {
					sqlSession.flushStatements();
					System.err.println("flushStatements()");
				}

				index++;
			}
			sqlSession.flushStatements();
			System.err.println("flushStatements()");
		} catch (Exception e) {
			log.error("批量操作发生异常：{}", e.getMessage(), e);

			// catch 异常后为了使 @Transaction 生效，这里必须再度抛出
			throw e;
		}
	}

}
