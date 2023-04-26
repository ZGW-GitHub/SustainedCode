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

	@PostMapping("batchSaveByMybatis")
	public String batchSaveByMybatis() {
		userService.batchSaveByMybatis();

		return "SUCCESS";
	}

	@PostMapping("batchSaveByCustom")
	public String batchSaveByCustom() {
		userService.batchSaveByCustom();

		return "SUCCESS";
	}

}
