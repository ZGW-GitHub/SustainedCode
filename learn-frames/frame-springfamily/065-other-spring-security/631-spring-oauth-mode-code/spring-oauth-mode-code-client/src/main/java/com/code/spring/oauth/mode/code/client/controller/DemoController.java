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

package com.code.spring.oauth.mode.code.client.controller;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2023/6/1 16:22
 */
@Slf4j
@RestController
public class DemoController {

	/**
	 * 防止 404
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("currentUser")
	public String currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return JSONUtil.toJsonStr(authentication);
	}

	@GetMapping("currentSession")
	public String session(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		return JSONUtil.toJsonStr(session);
	}

}