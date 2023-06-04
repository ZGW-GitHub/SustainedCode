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

package com.code.spring.oauth.mode.code.resource.configuration;

import com.code.spring.oauth.mode.code.resource.component.CustomAccessDeniedHandler;
import com.code.spring.oauth.mode.code.resource.component.CustomAuthenticationEntryPoint;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 资源服务器配置
 *
 * @author Snow
 * @date 2023/5/31 20:28
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class OAuthConfiguration {

	/**
	 * 拒绝访问处理器 401
	 */
	@Resource
	private CustomAccessDeniedHandler customAccessDeniedHandler;

	/**
	 * 认证失败处理器 403
	 */
	@Resource
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	public void configForHttpSecurity(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.oauth2ResourceServer(configurer -> configurer
				.accessDeniedHandler(customAccessDeniedHandler)
				.authenticationEntryPoint(customAuthenticationEntryPoint).jwt(withDefaults()));
	}

}
