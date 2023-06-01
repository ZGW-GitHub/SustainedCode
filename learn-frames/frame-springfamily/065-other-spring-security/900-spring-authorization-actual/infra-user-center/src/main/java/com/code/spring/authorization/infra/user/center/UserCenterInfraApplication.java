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

package com.code.spring.authorization.infra.user.center;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Snow
 * @date 2023/6/1 13:26
 */
@Slf4j
@SpringBootApplication
public class UserCenterInfraApplication {
	public static void main(String[] args) {

		new SpringApplicationBuilder(UserCenterInfraApplication.class).run(args);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("UserCenter 认证信息：{}", auth);

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.stream().iterator();
		ArrayList<Object> authList = new ArrayList<>();
		while (iterator.hasNext()) {
			authList.add(iterator.next().getAuthority());
		}
		log.info("UserCenter 认证解析，name : {} , authorities : {}", auth.getName(), authList);
	}
}
