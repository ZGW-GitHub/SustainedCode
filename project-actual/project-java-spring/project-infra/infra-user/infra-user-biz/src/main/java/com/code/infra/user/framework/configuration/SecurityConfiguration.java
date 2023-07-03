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

package com.code.infra.user.framework.configuration;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.code.infra.user.framework.component.filter.TokenFilter;
import com.code.infra.user.framework.component.security.CustomAuthenticationProvider;
import com.code.infra.user.framework.config.SecurityConfig;
import com.code.infra.user.mvc.service.UserInfoService;
import com.code.infra.user.mvc.service.domain.UserAuthBO;
import com.code.infra.user.mvc.service.domain.UserAuthDTO;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Objects;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Snow
 * @date 2023/5/9 16:27
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = true)
public class SecurityConfiguration {

	@Resource
	private UserInfoService userInfoService;

	@Resource
	private SecurityConfig securityConfig;

	/**
	 * Security 过滤器链
	 *
	 * @param httpSecurity httpSecurity
	 *
	 * @return {@link SecurityFilterChain}
	 *
	 * @throws Exception 异常
	 */
	@Bean
	SecurityFilterChain sysUserSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.securityMatcher("/**")
				.authorizeHttpRequests(config -> config
						.requestMatchers(ArrayUtil.toArray(securityConfig.getWhiteList(), String.class)).permitAll()
						// .requestMatchers("/resource/one").hasAuthority("ROLE_USER")
						// .requestMatchers("/resource/two").hasAuthority("ROLE_NONE")
						.requestMatchers("/resource/one").hasRole("USER")
						.requestMatchers("/resource/two").hasRole("NONE")
						.anyRequest().authenticated())
				.addFilterBefore(new TokenFilter(userInfoService, securityConfig), UsernamePasswordAuthenticationFilter.class)
				.formLogin(configurer -> configurer
						.loginPage("/userLifecycle/login")
						.loginProcessingUrl("/userLifecycle/login")
						.successForwardUrl("/userLifecycle/loginSuccess"))
				.cors(withDefaults())
				.csrf(configurer -> configurer.ignoringRequestMatchers("/userLifecycle/**", "/resource/**"))
				.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 登录后还会跳转到登录页
		;

		return httpSecurity.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return account -> {
			UserAuthDTO authInfoDTO = userInfoService.findAuthInfo(new UserAuthBO().setAccount(account));
			if (Objects.isNull(authInfoDTO)) {
				throw new UsernameNotFoundException("用户不存在！");
			}

			return authInfoDTO;
		};
	}

	/**
	 * 身份验证提供者
	 *
	 * @return {@link AuthenticationProvider}
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new CustomAuthenticationProvider(userDetailsService(), passwordEncoder());
	}

	/**
	 * 密码编码器
	 *
	 * @return {@link PasswordEncoder}
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		// 不需要编码，存储时已经加密了
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return StrUtil.equals(rawPassword, encodedPassword);
			}
		};
	}

}
