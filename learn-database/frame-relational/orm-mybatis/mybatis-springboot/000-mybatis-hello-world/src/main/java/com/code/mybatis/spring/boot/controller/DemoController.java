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

package com.code.mybatis.spring.boot.controller;

import cn.hutool.core.util.RandomUtil;
import com.code.mybatis.spring.boot.dal.dos.User;
import com.code.mybatis.spring.boot.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2022/10/17 10:01
 */
@Slf4j
@RestController
public class DemoController {

	@Resource
	private UserService userService;

	@PostMapping("demo")
	public String demo() {
		userService.demo(new User().setRecordId(RandomUtil.randomLong(1000000, 10000000)).setName("test").setAge(18));

		return "SUCCESS";
	}

	@PostMapping("transaction")
	public String transaction() {
		userService.transaction(new User().setName("test").setAge(18));

		return "SUCCESS";
	}

	@PostMapping("batch")
	public String batch() {
		userService.batchSave2();

		return "SUCCESS";
	}

}
