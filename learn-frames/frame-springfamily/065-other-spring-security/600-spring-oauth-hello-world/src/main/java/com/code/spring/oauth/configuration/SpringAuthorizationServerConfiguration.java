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

package com.code.spring.oauth.configuration;

import cn.hutool.core.util.IdUtil;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.time.Duration;

/**
 * @author Snow
 * @date 2023/5/31 13:06
 */
@Slf4j
@Configuration
public class SpringAuthorizationServerConfiguration {

	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		// 启用 OpenID Connect 1.0
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
		// 当未从授权端点进行身份验证时，重定向到登录页
		http.exceptionHandling((exceptions) -> exceptions.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"), new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));
		// 接受用户信息/客户端注册的访问令牌
		http.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));

		return http.build();
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		// Token 配置：TTL 、是否复用 refreshToken 、等等
		TokenSettings tokenSettings = TokenSettings.builder()
				// accessToken 存活时间：2 小时
				.accessTokenTimeToLive(Duration.ofHours(2))
				// refreshToken 存活时间：30天（ 30 天内当令牌过期时，可以用刷新令牌重新申请新令牌，不需要再认证 ）
				.refreshTokenTimeToLive(Duration.ofDays(30))
				// 是否复用 refreshToken
				.reuseRefreshTokens(true)
				.build();

		// 客户端配置
		ClientSettings clientSettings = ClientSettings.builder()
				// 是否需要用户授权确认
				.requireAuthorizationConsent(true)
				.build();

		// http://notuptoyou.com:65000/oauth2/authorize?client_id=demo-client&response_type=code&scope=demo.read%20demo.write&redirect_uri=https://www.bing.com
		RegisteredClient oidcClient = RegisteredClient.withId(IdUtil.randomUUID())
				.clientId("demo-client")
				.clientSecret("{noop}secret")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUri("http://notuptoyou.com:8080/login/oauth2/code/demo-client-aaa")
				.redirectUri("https://www.bing.com")
				.postLogoutRedirectUri("https://www.google.com")
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.scope("demo.read")
				.scope("demo.write")
				.tokenSettings(tokenSettings)
				.clientSettings(clientSettings)
				.build();

		return new InMemoryRegisteredClientRepository(oidcClient);
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

}
