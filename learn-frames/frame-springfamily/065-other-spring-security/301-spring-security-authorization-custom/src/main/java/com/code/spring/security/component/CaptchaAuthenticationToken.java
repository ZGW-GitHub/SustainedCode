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

package com.code.spring.security.component;

import com.code.spring.security.dal.dos.SysUser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Snow
 * @date 2023/6/3 14:12
 */
@Slf4j
@Getter
public class CaptchaAuthenticationToken extends AbstractAuthenticationToken {

	private final String  username;
	private final String  captcha;
	private final SysUser principal;
	private final String  credentials;

	public CaptchaAuthenticationToken(String username, String captcha, String credentials) {
		super(null);
		this.username = username;
		this.captcha = captcha;
		this.principal = null;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	public CaptchaAuthenticationToken(Collection<? extends GrantedAuthority> authorities, SysUser principal, String credentials) {
		super(authorities);
		this.username = null;
		this.captcha = null;
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(true);
	}

	@Override
	public String getCredentials() {
		return credentials;
	}

	@Override
	public SysUser getPrincipal() {
		return principal;
	}

}
