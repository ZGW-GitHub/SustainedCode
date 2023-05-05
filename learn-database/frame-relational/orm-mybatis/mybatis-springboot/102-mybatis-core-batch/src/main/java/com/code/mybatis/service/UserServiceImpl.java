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

import com.code.mybatis.dal.dos.User;
import com.code.mybatis.dal.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.LongStream;

/**
 * @author Snow
 * @date 2022/10/17 09:59
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private SqlSessionFactory sqlSessionFactory;

	@Override
	@Transactional
	public void batchSave() {
		List<User> userList = LongStream.rangeClosed(1, 20).boxed()
				.map(i -> new User().setName("test" + i).setAge(i.intValue()))
				.toList();

		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class); // 必须通过 SqlSession 获取，不能用注入的
		try {
			int index = 1;
			for (User user : userList) {
				userMapper.save(user);

				if (index % 6 == 0) {
					sqlSession.flushStatements();
					System.err.println("flushStatements()");
				}

				if (index == 19) {
					throw new NumberFormatException();
				}

				index++;
			}
			// 通过源码可以知道：当使用了 Spring 时，这里会调用 SpringManagedTransaction 的 commit() ，关于该方法的详解请看文章：Mybatis 下的 《 SqlSession 使用详解 》。
			// 我们只需知道这里的 sqlSession.commit() 并不会提交事务。
			// TODO 既然这里并不会回滚事务，那么可以对其进行下改造，请看 batchSave2()
			sqlSession.commit();
		} catch (Exception e) {
			// 通过源码可以知道：当使用了 Spring 时，这里会调用 SpringManagedTransaction 的 rollback() ，关于该方法的详解请看文章：Mybatis 下的 《 SqlSession 使用详解 》。
			// 我们只需知道这里的 sqlSession.rollback() 并不会回滚事务。
			// TODO 既然这里并不会回滚事务，那么可以对其进行下改造，请看 batchSave2()
			sqlSession.rollback();
			log.error("批量操作发生异常：{}", e.getMessage(), e);

			// catch 异常后为了使 @Transaction 生效，这里必须再度抛出
			throw e;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	@Transactional
	public void batchSave2() {
		List<User> userList = LongStream.rangeClosed(1, 20).boxed()
				.map(i -> new User().setName("test" + i).setAge(i.intValue()))
				.toList();

		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try (sqlSession) {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class); // 必须通过 SqlSession 获取，不能用注入的
			int index = 1;
			for (User user : userList) {
				userMapper.save(user);

				if (index % 6 == 0) {
					sqlSession.flushStatements();
					System.err.println("flushStatements()");
				}

				if (index == 19) {
					throw new NumberFormatException();
				}

				index++;
			}
			// 删除了 sqlSession.commit() ，这里必须调用下 flushStatements() ，以防止遗漏数据
			sqlSession.flushStatements();
			System.err.println("flushStatements()");
		} catch (Exception e) {
			log.error("批量操作发生异常：{}", e.getMessage(), e);

			// catch 异常后为了使 @Transaction 生效，这里必须再度抛出
			throw e;
		}
	}

}
