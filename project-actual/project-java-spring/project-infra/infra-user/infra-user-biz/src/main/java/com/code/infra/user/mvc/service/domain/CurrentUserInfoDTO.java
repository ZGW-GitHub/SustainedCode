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

package com.code.infra.user.mvc.service.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @author Snow
 * @date 2023/7/5 22:36
 */
@Slf4j
@Data
@Accessors(chain = true)
public class CurrentUserInfoDTO {

	/**
	 * 用户信息 ID
	 */
	private String recordId;

	/**
	 * 账户
	 */
	private String account;

	/**
	 * 授予的权限
	 */
	private List<String> grantedAuthority;

	/**
	 * 令牌
	 */
	private String token;

	public List<String> getGrantedAuthority() {
		return Arrays.asList("ROLE_ADMIN", "ROLE_USER");
	}

}
