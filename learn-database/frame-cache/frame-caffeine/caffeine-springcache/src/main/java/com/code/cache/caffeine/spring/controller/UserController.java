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

package com.code.cache.caffeine.spring.controller;

import cn.hutool.json.JSONUtil;
import com.code.cache.caffeine.spring.entity.User;
import com.code.cache.caffeine.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Snow
 * @date 2022/5/3 16:30
 */
@Slf4j
@RestController
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("save/{userid}")
	public String save(@PathVariable("userid") String userid) {
		userService.save(new User().setId(Integer.valueOf(userid)).setAge(10).setName("test"));

		return "SUCCESS";
	}

	@PostMapping("select/{userid}")
	public String select(@PathVariable("userid") String userid) {
		User user = userService.get(Integer.valueOf(userid));
		user = Optional.ofNullable(user).orElse(new User());

		return JSONUtil.parse(user).toStringPretty();
	}

}
