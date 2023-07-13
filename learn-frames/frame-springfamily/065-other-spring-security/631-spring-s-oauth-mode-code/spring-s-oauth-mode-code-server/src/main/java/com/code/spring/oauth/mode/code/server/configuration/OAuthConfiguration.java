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

import cn.hutool.core.util.IdUtil;
import com.code.spring.oauth.mode.code.server.component.RedisSecurityContextRepository;
import com.code.spring.oauth.mode.code.server.config.OAuthConfig;
import com.nimbusds.jose.jwk.source.JWKSource;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.time.Duration;
import java.util.Objects;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Snow
 * @date 2023/5/30 22:22
 */
@Slf4j
@Data
@Configuration
public class OAuthConfiguration {

	@Resource
	private OAuthConfig OAuthConfig;

	@Resource
	private JWKSource jwkSource;

	@Resource
	private RedisSecurityContextRepository securityContextRepository;

	/**
	 * 授权配置
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain oAuthSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
		oAuth2AuthorizationServerConfigurer.oidc(withDefaults()); // 启用 OpenID Connect 1.0

		RequestMatcher endpointsMatcher = oAuth2AuthorizationServerConfigurer.getEndpointsMatcher();
		httpSecurity.securityMatcher(endpointsMatcher)
				.authorizeHttpRequests(configurer -> configurer
						.anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
				.cors(withDefaults())
				.securityContext(configurer -> configurer
						.securityContextRepository(securityContextRepository))
				.exceptionHandling(configurer -> configurer
						.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
				.apply(oAuth2AuthorizationServerConfigurer);

		return httpSecurity.build();
	}

	/**
	 * Registered Client 存储库
	 *
	 * @param jdbcTemplate jdbcTemplate
	 * @return Registered Client 存储库
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

		String clientId = OAuthConfig.getDefaultClientId();
		// 1、检查当前客户端是否已注册
		RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);

		if (Objects.isNull(registeredClient)) {
			// 2、添加客户端
			registeredClient = this.createRegisteredClient(clientId);
			registeredClientRepository.save(registeredClient);
		}

		return registeredClientRepository;
	}

	/**
	 * 定义客户端（令牌申请方式：授权码模式）
	 *
	 * @param clientId 客户端 ID
	 * @return Registered Client
	 */
	private RegisteredClient createRegisteredClient(final String clientId) {
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
				.requireAuthorizationConsent(false)
				.build();

		// http://notuptoyou.com:65000/oauth2/authorize?client_id=demo-client&response_type=code&scope=read%20write&redirect_uri=http://127.0.0.1:65001/login/oauth2/code/first-registration
		RegisteredClient.Builder registeredClientBuilder = RegisteredClient
				.withId("Test_" + IdUtil.randomUUID())
				.clientId(clientId)
				// .clientSecret("TODO" + PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(authorizationServerConfig.getDefaultClientSecret()))
				.clientSecret("{noop}" + OAuthConfig.getDefaultClientSecret())
				.clientName(OAuthConfig.getDefaultClientName())
				// 客户端认证方式
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				// 授权模式
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 授权码模式
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // 刷新令牌（授权码模式）
				// .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				// set 上面创建的 token 配置
				.tokenSettings(tokenSettings)
				// set 上面创建的 客户端配置
				.clientSettings(clientSettings);

		// 对该客户端的授权范围
		OAuthConfig.getDefaultClientScopes().forEach(registeredClientBuilder::scope);

		// 回调地址：授权服务器向当前客户端响应时调用下面地址，不在此列的地址将被拒绝（ 只能使用 IP/域名，不能使用 localhost ）
		OAuthConfig.getDefaultClientRedirectUris().forEach(registeredClientBuilder::redirectUri);

		return registeredClientBuilder.build();
	}

	/**
	 * OAuth2 授权 Service
	 *
	 * @param jdbcTemplate               操作数据库
	 * @param registeredClientRepository 客户端仓库
	 * @return OAuth2AuthorizationService ：通过 JDBC 操作令牌发放记录
	 */
	@Bean
	public OAuth2AuthorizationService auth2AuthorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
	}

	/**
	 * OAuth2 授权确认 Service
	 *
	 * @param jdbcTemplate               操作数据库
	 * @param registeredClientRepository 客户端仓库
	 * @return OAuth2AuthorizationConsentService ：通过 JDBC 把资源拥有者授权确认操作保存到数据库
	 */
	@Bean
	public OAuth2AuthorizationConsentService auth2AuthorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
	}

	/**
	 * 授权服务器配置：授权服务器本身也提供了一个配置工具来配置其元信息，大多数都使用默认配置即可，唯一需要配置的其实只有授权服务器的地址 issuer 在生产中这个地方应该配置为域名
	 *
	 * @return Authorization Server Settings
	 */
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().issuer(OAuthConfig.getIssuer()).build();
	}

}
