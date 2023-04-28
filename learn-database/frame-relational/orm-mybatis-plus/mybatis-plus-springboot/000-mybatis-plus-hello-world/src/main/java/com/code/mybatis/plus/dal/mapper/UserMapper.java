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

package com.code.mybatis.plus.dal.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.code.mybatis.plus.dal.dos.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Snow
 * @date 2020/8/14 11:49 上午
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	default List<User> selectByAge(Integer age) {
		List<User> userList;

		// 1、Wrapper
		QueryWrapper<User> wrapper = Wrappers.query(new User()).eq("age", age);
		userList = selectList(wrapper);
		userList.forEach(System.err::println);

		// 2、LambdaWrapper
		LambdaQueryWrapper<User> lambdaWrapper = Wrappers.lambdaQuery(User.class).eq(User::getAge, age);
		userList = selectList(lambdaWrapper);
		userList.forEach(System.err::println);

		// 3、ChainWrapper
		userList = ChainWrappers.queryChain(this).eq("age", age).list();
		userList.forEach(System.err::println);

		// 4、LambdaChainWrapper
		userList = ChainWrappers.lambdaQueryChain(this).eq(User::getAge, age).list();
		userList.forEach(System.err::println);

		return userList;
	}

}
