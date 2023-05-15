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

package com.code.spring.security.configuration;

import com.code.spring.security.component.filter.DemoFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Snow
 * @date 2023/5/9 16:27
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SpringSecurityConfiguration {

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			/**
			 * 根据用户名加载用户
			 *
			 * @param username 用户名
			 * @return {@link UserDetails}
			 * @throws UsernameNotFoundException 用户没有找到
			 */
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return User.withUsername(username) // 用户名
						.password("123456") // 密码
						.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode) // 密码编码器
						.authorities("ROLE_USER", "ROLE_ADMIN") // 权限集
						.build();
			}
		};
	}

	/**
	 * 访客过滤器链
	 *
	 * @param httpSecurity httpSecurity
	 * @return {@link SecurityFilterChain}
	 * @throws Exception 异常
	 */
	@Bean
	@Order(1)
	SecurityFilterChain visitorSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.securityMatcher("/visitor/**")
				.authorizeHttpRequests().anyRequest().permitAll()
				.and()
				.addFilterBefore(new DemoFilter(), UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	/**
	 * 系统用户过滤器链
	 *
	 * @param httpSecurity httpSecurity
	 * @return {@link SecurityFilterChain}
	 * @throws Exception 异常
	 */
	@Bean
	@Order(2)
	SecurityFilterChain sysUserSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.securityMatcher("/**")
				.authorizeHttpRequests().anyRequest().authenticated()
				.and()
				.addFilterBefore(new DemoFilter(), UsernamePasswordAuthenticationFilter.class)
				.formLogin();

		return httpSecurity.build();
	}

}
