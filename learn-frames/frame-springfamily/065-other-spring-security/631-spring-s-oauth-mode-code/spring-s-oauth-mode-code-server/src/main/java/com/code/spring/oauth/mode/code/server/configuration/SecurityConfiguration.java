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

package com.code.spring.oauth.mode.code.server.configuration;

import com.code.spring.oauth.mode.code.server.config.SecurityConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Snow
 * @date 2023/5/30 22:00
 */
@Slf4j
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration {

	@Resource
	private SecurityConfig securityConfig;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.securityMatcher("/**")
				.authorizeHttpRequests(configurer -> configurer
						.requestMatchers("/test", "/currentUser", "/currentSession").authenticated()
						.anyRequest().permitAll())
				.csrf(withDefaults())
				.cors(withDefaults())
				.formLogin(configurer -> configurer
						.loginPage("/login")
						// .failureHandler((request, response, exception) -> {
						// 	System.err.println("登录发生异常：" + ExceptionUtil.getRootCauseMessage(exception));
						// })
				)
				.logout(withDefaults());

		return httpSecurity.build();
	}

	/**
	 * 模拟用户
	 */
	@Bean
	UserDetailsService users() {
		UserDetails user = User.builder()
				.username(securityConfig.getDefaultUserName())
				.password(securityConfig.getDefaultUserPassword())
				.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
				.roles(securityConfig.getDefaultUserRoles().toArray(new String[0]))
				// .authorities("SCOPE_userinfo")
				.build();

		return new InMemoryUserDetailsManager(user);
	}

	/**
	 * 也会生成一个 SecurityFilterChain
	 *
	 * @return {@link WebSecurityCustomizer}
	 */
	// @Bean
	// WebSecurityCustomizer webSecurityCustomizer() {
	// 	return webSecurity -> webSecurity.ignoring().requestMatchers(new AntPathRequestMatcher("/h2/**"));
	// }

}
