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

package com.code.spring.oauth.mode.code.server.controller;

import com.code.spring.oauth.mode.code.server.config.SecurityConfig;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Snow
 * @date 2023/6/28 14:44
 */
@Slf4j
@Controller
public class LoginController {

	@Resource
	private SecurityConfig securityConfig;

	private String loginProviderUrl;

	private String loginSuccessUrl;

	@GetMapping(path = "login", produces = MediaType.TEXT_HTML_VALUE)
	public String loginPage(HttpServletRequest request) {
		if (loginProviderUrl == null) {
			loginProviderUrl = "redirect:" + securityConfig.getLoginProviderUrl();
		}
		return loginProviderUrl;
	}

	@GetMapping("/")
	public String loginSuccess() {
		if (loginSuccessUrl == null) {
			loginSuccessUrl = "redirect:" + securityConfig.getLoginSuccessUrl();
		}
		return loginSuccessUrl;
	}

}