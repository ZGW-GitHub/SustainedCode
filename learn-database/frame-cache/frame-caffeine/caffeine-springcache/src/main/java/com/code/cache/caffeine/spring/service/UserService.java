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

package com.code.cache.caffeine.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.code.cache.caffeine.spring.entity.User;

import javax.annotation.Resource;

/**
 * @author Snow
 * @date 2020/9/1 9:20 上午
 */
@Slf4j
@Service
public class UserService {

	/**
	 * 调用该方法后，会将返回值缓存（ key = "userCache::#user.id" ）；
	 * 若无返回值会缓存错误信息
	 *
	 * @param user user
	 */
	@CachePut(value = "userCache", key = "#user.id")
	public User save(User user) {
		return user;
	}

	/**
	 * 调用该方法时，会先从名为 userCache 的缓存中找，找到了直接返回；
	 * 找不到，执行方法体中的代码，并将方法的返回值缓存起来（ key = "userCache::#id" ）
	 *
	 * @param id key
	 *
	 * @return user
	 */
	@Cacheable(value = "userCache", key = "#id")
	public User get(Integer id) {
		return new User();
	}

	/**
	 * 调用该方法后会将 key="userCache::#id" 的缓存从名为 userCache 的缓存中删除
	 *
	 * @param id userid
	 */
	@CacheEvict(value = "userCache", key = "#id")
	public void delete(Integer id) {
		return;
	}

}
