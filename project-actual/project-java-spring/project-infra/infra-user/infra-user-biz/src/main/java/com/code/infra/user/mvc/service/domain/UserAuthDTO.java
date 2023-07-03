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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Snow
 * @date 2023/7/3 20:57
 */
@Slf4j
@Data
@Accessors(chain = true)
public class UserAuthDTO implements UserDetails, Serializable {

	/**
	 * 用户账号
	 */
	private String account;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 授予的权限
	 */
	private List<String> grantedAuthority;

	@Override
	public String getUsername() {
		return account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthority.stream().map(SimpleGrantedAuthority::new).toList();
	}

	/**
	 * 账号是否未过期
	 *
	 * @return boolean
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 账号是否未锁定
	 *
	 * @return boolean
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 密码是否未过期
	 *
	 * @return boolean
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 账号是否激活
	 *
	 * @return boolean
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}