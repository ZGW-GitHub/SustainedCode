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

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Snow
 * @date 2023/6/1 15:21
 */
@Slf4j
@Configuration
public class AuthorizationServerConfiguration {

	@Value("${spring.security.oauth2.client.registration.first-registration.client-id}")
	private String clientId;

	@Bean
	SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.eraseCredentials(false);

		return httpSecurity.securityMatcher("/**")
				.authorizeHttpRequests(configurer -> configurer
						.requestMatchers("/redirect", "/redirect/**").permitAll()
						.requestMatchers("/favicon.ico").permitAll()
						.anyRequest().authenticated())
				.oauth2Login(configurer -> configurer
						.loginPage("/oauth2/authorization/first-registration"))
				.oauth2Client(withDefaults())
				.logout(withDefaults())
				.build();
	}

}
