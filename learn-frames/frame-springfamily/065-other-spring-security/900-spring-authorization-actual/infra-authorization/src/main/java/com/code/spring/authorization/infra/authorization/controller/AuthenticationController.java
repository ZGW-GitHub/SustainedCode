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

package com.code.spring.authorization.infra.authorization.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2023/5/30 23:09
 */
@Slf4j
@RestController
@RequestMapping("oauth2")
public class AuthenticationController {

	/**
	 * 客户通过认证授权后，可以通过该接口获取 user 的信息
	 *
	 * @return Authentication
	 */
	@GetMapping("user")
	public JSONObject oauth2UserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.debug("【 获取用户信息 】{}", authentication);

		if (authentication == null) {
			throw new RuntimeException("无有效认证的用户！");
		}
		return JSONUtil.parseObj(authentication);
	}

}
