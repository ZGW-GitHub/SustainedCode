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

package com.code.mybatis.plus.controller;

import com.code.mybatis.plus.dal.dos.User;
import com.code.mybatis.plus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @author Snow
 * @date 2022/10/14 18:05
 */
@Slf4j
@RestController
public class DemoController {

	@Resource
	private UserService userService;

	@PostMapping("transaction")
	public String transaction() {
		User user = User.builder().name("test").age(11).createTime(new Date()).build();

		userService.transaction(user);

		return "SUCCESS";
	}

	@PostMapping("batchA")
	public String batch_a() {
		List<User> userList = LongStream.rangeClosed(1, 20).boxed()
				.map(i -> {
					if (i == 17) {
						return new User().setName("这里名字超出限制，在保存时会发生异常").setAge(i.intValue());
					}
					return new User().setName("test" + i).setAge(i.intValue());
				}).toList();

		userService.saveBatch(userList, 6);

		return "SUCCESS";
	}

	@PostMapping("batchB")
	public String batch_b() {
		userService.batch_b();

		return "SUCCESS";
	}

	@PostMapping("batchC")
	public String batch_c() {
		userService.batch_c();

		return "SUCCESS";
	}

}
