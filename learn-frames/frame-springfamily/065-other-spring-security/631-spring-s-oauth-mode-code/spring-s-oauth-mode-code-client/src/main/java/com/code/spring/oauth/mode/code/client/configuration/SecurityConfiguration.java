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

package com.code.spring.oauth.mode.code.client.configuration;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Snow
 * @date 2023/6/1 15:21
 */
@Slf4j
@Configuration
public class SecurityConfiguration {

	@Resource
	private OAuthConfiguration oAuthConfiguration;

	@Bean
	SecurityFilterChain oAuthSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
		// authenticationManagerBuilder.eraseCredentials(false);

		httpSecurity.securityMatcher("/**")
				.authorizeHttpRequests(configurer -> configurer
						.requestMatchers("/favicon.ico", "/login", "/index").permitAll()
						.anyRequest().authenticated())
				// .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.logout(withDefaults());

		// 执行 OAuth 相关的配置
		oAuthConfiguration.configByHttpSecurity(httpSecurity);

		return httpSecurity.build();
	}

}
