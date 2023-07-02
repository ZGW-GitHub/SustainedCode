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

package com.code.spring.security.controller;

import cn.hutool.json.JSONUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2023/4/30 11:47
 */
@RestController
@RequestMapping("resource")
public class ResourceController {

	@RequestMapping("one")
	public String one() {
		System.err.println("one 收到请求");
		return "one";
	}

	@RequestMapping("two")
	public String two() {
		System.err.println("two 收到请求");
		return "two";
	}

	@GetMapping("currentUser")
	public String oidcIdToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return JSONUtil.toJsonStr(authentication);
	}

}
