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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Snow
 * @date 2023/6/4 15:28
 */
@Slf4j
@Configuration
@EnableWebSecurity
@ConditionalOnBean(JwtDecoder.class)
public class SecurityConfiguration {

	@Resource
	private OAuthConfiguration oAuthConfiguration;

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

	/**
	 * 资源管理器配置
	 *
	 * @param httpSecurity httpSecurity
	 *
	 * @return security filter chain
	 *
	 * @throws Exception exception
	 */
	@Bean
	SecurityFilterChain jwtSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// 设置 security 的 session 生成策略为：不创建
				.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
						.requestMatchers("/one/aaa").hasAnyAuthority("SCOPE_read", "SCOPE_all")
						.requestMatchers("/one/bbb").hasAnyAuthority("SCOPE_write", "SCOPE_all")
						.anyRequest().authenticated())
				.exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
						.accessDeniedHandler(customAccessDeniedHandler)
						.authenticationEntryPoint(customAuthenticationEntryPoint))
		// .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		;

		oAuthConfiguration.configForHttpSecurity(httpSecurity);

		return httpSecurity.build();
	}

}
