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

package com.code.spring.authorization.infra.user.center.configuration;

import com.code.spring.authorization.service.one.component.CustomAccessDeniedHandler;
import com.code.spring.authorization.service.one.component.CustomAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 资源服务器配置
 *
 * @author Snow
 * @date 2023/5/31 20:28
 */
@Slf4j
@ConditionalOnBean(JwtDecoder.class)
@Configuration(proxyBeanMethods = false)
public class OAuth2ResourceServerConfiguration {

	/**
	 * 资源管理器配置
	 *
	 * @param httpSecurity httpSecurity
	 * @return security filter chain
	 * @throws Exception exception
	 */
	@Bean
	SecurityFilterChain jwtSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// 拒绝访问处理器 401
		CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
		// 认证失败处理器 403
		CustomAuthenticationEntryPoint customAuthenticationEntryPoint = new CustomAuthenticationEntryPoint();

		return httpSecurity
				// 设置 security 的 session 生成策略为：不创建
				.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
						.requestMatchers("/user/one").hasAnyAuthority("SCOPE_service.read", "SCOPE_all")
						.requestMatchers("/user/two").hasAnyAuthority("SCOPE_service.write", "SCOPE_all")
						.anyRequest()
						.authenticated())
				.exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
						.accessDeniedHandler(customAccessDeniedHandler)
						.authenticationEntryPoint(customAuthenticationEntryPoint))
				.oauth2ResourceServer(oAuth2ResourceServerConfigurer -> oAuth2ResourceServerConfigurer
						.accessDeniedHandler(customAccessDeniedHandler)
						.authenticationEntryPoint(customAuthenticationEntryPoint).jwt(withDefaults()))
				.build();
	}

	/**
	 * JWT 个性化解析
	 *
	 * @return JWT 认证转换器
	 */
	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		// 如果不按照规范解析权限集合 Authorities 就需要自定义 key
		// jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scopes");
		// OAuth2 默认前缀是 "SCOPE_" 、Spring Security 是 "ROLE_"
		// jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		jwtAuthenticationConverter.setPrincipalClaimName(JwtClaimNames.SUB);
		return jwtAuthenticationConverter;
	}

}
