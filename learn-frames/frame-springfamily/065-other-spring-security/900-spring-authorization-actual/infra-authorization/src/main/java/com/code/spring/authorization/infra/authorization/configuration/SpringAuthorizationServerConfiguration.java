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

package com.code.spring.authorization.infra.authorization.configuration;

import cn.hutool.core.util.IdUtil;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
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

import java.security.KeyStore;
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
public class SpringAuthorizationServerConfiguration {

	@Value("${keystore.path}")
	private String keystorePath;

	/**
	 * 授权配置
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
		// 启用 OpenID Connect 1.0
		httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(withDefaults());
		// 接受用户信息/客户端注册的访问令牌
		httpSecurity.oauth2ResourceServer((oAuth2ResourceServerConfigurer) -> oAuth2ResourceServerConfigurer.jwt(withDefaults()));
		// 当未从授权端点进行身份验证时，重定向到登录页
		httpSecurity.exceptionHandling((exceptions) -> exceptions.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"), new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));

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

		String clientId = "user_center";
		// 1、检查当前客户端是否已注册
		RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);

		if (Objects.isNull(registeredClient)) {
			// 2、添加客户端
			registeredClient = this.createRegisteredClientAuthorizationCode(clientId);
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
	private RegisteredClient createRegisteredClientAuthorizationCode(final String clientId) {
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

		// http://notuptoyou.com:65000/oauth2/authorize?client_id=user_center&response_type=code&scope=read%20write&redirect_uri=https://www.bing.com
		// http://notuptoyou.com:65000/oauth2/authorize?client_id=user_center&response_type=code&scope=read%20write&redirect_uri=http://127.0.0.1:65001/login/oauth2/code/userCenter
		String clientSecret = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456");
		log.debug("createRegisteredClientAuthorizationCode : clientSecret[{}]", clientSecret);

		return RegisteredClient
				.withId("Test_" + IdUtil.randomUUID())
				.clientId(clientId)
				.clientSecret("{noop}" + clientId + "_123456")
				.clientName(String.format("客户端[%s]", clientId))
				// 客户端认证方式
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				// 授权模式
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 授权码模式
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // 刷新令牌（授权码模式）
				// 回调地址：授权服务器向当前客户端响应时调用下面地址，不在此列的地址将被拒绝（ 只能使用 IP/域名，不能使用 localhost ）
				.redirectUri("http://127.0.0.1:65001/login/oauth2/code/user_center")
				.redirectUri("http://127.0.0.1:65001")
				.redirectUri("https://www.bing.com")
				// 对该客户端的授权范围
				.scope("read")
				.scope("write")
				// set 上面创建的 token 配置
				.tokenSettings(tokenSettings)
				// set 上面创建的 客户端配置
				.clientSettings(clientSettings)
				.build();
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
	 * 加载 jwk 资源（用于生成令牌）
	 *
	 * @return JWK Source
	 */
	@SneakyThrows
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		// 证书的路径
		String keyFilePath = keystorePath + "/demoKey.jks";
		// 证书别名
		String keyAlias = "demoKey";
		// keystore 密码
		char[] keystorePassword = "123456".toCharArray();

		KeyStore jks = KeyStore.getInstance("jks");
		jks.load(new FileSystemResource(keyFilePath).getInputStream(), keystorePassword);
		RSAKey rsaKey = RSAKey.load(jks, keyAlias, keystorePassword);

		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	/**
	 * 授权服务器配置：授权服务器本身也提供了一个配置工具来配置其元信息，大多数都使用默认配置即可，唯一需要配置的其实只有授权服务器的地址 issuer 在生产中这个地方应该配置为域名
	 *
	 * @return Authorization Server Settings
	 */
	@Bean
	public AuthorizationServerSettings providerSettings() {
		return AuthorizationServerSettings.builder().issuer("http://notuptoyou.com:65000").build();
	}

}
