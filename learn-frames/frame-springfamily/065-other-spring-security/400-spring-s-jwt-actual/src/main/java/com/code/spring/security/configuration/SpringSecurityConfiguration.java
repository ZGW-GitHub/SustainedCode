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

import com.code.spring.security.dal.dos.SysUser;
import com.code.spring.security.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Snow
 * @date 2023/5/9 16:27
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = true)
public class SpringSecurityConfiguration {

	@Resource
	private SysUserService sysUserService;

	/**
	 * Security 过滤器链
	 *
	 * @param httpSecurity httpSecurity
	 * @return {@link SecurityFilterChain}
	 * @throws Exception 异常
	 */
	@Bean
	SecurityFilterChain sysUserSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.securityMatcher("/**")
				.authorizeHttpRequests(config -> config
						.requestMatchers("/favicon.ico").permitAll()
						.anyRequest().authenticated())
				.formLogin(withDefaults())
				.cors(withDefaults())
				.csrf(withDefaults())
		// .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 登录后还会跳转到登录页
		;

		return httpSecurity.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return username -> {
			SysUser sysUser = sysUserService.findByAccount(username);
			if (Objects.isNull(sysUser)) {
				throw new UsernameNotFoundException("用户不存在！");
			}

			return User.withUsername(username) // 账号
					.password(sysUser.getPassword()) // 密码
					.passwordEncoder(passwordEncoder()::encode) // 密码编码器
					.authorities("ROLE_USER", "ROLE_ADMIN") // 权限集
					.build();
		};
	}

	/**
	 * 身份验证提供者
	 *
	 * @return {@link AuthenticationProvider}
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		// 设置获取用户信息的 service
		authProvider.setUserDetailsService(userDetailsService());
		// 设置密码加密器
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	/**
	 * 密码编码器
	 *
	 * @return {@link PasswordEncoder}
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}