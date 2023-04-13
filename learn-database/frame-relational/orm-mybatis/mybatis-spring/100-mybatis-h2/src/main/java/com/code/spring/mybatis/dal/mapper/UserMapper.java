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

package com.code.spring.mybatis.dal.mapper;

import com.code.spring.mybatis.dal.dos.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Snow
 * @date 2020/8/14 11:49 上午
 */
public interface UserMapper {

	/**
	 * 保存实体
	 *
	 * @param user 实体
	 * @return 影响行数
	 */
	int save(User user);

	void updateName(@Param("name") String name, @Param("id") Integer userid);

	List<User> listAll();

	List<User> findById(Integer userid);

	User selectByLock(Integer userid);

}
