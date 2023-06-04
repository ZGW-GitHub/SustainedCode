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

package com.code.spring.oauth.mode.code.resource.configuration;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Collection;

/**
 * @author Snow
 * @date 2023/5/31 19:50
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class JwtConfiguration {

	/**
	 * 授权服务器地址
	 */
	@Value("${spring.oauth.custom.issuer}")
	private String issuer;

	/**
	 * jwt 有效期
	 */
	@Value("${spring.jwt.expires:0}")
	private Long expires;

	/**
	 * key 地址
	 */
	@Value("${spring.jwt.custom.key.location}")
	private String keyLocation;

	/**
	 * 校验 jwt issuer 是否合法
	 *
	 * @return jwt issuer validator
	 */
	@Bean
	JwtIssuerValidator jwtIssuerValidator() {
		return new JwtIssuerValidator(issuer);
	}

	/**
	 * 校验 jwt 是否过期
	 *
	 * @return jwt timestamp validator
	 */
	@Bean
	JwtTimestampValidator jwtTimestampValidator() {
		// 入参：为 0 时，和令牌实际时间一致。大于 0 时，会在原来过期时间的基础再加上入参值。所以这里可以不用配置这个值。
		return new JwtTimestampValidator(Duration.ofSeconds(expires));
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

	/**
	 * OAuth Token 委托校验器
	 *
	 * @param tokenValidators token validators
	 * @return delegating oauth2 token validator
	 */
	@Primary
	@Bean({"delegatingTokenValidator"})
	public DelegatingOAuth2TokenValidator<Jwt> delegatingTokenValidator(Collection<OAuth2TokenValidator<Jwt>> tokenValidators) {
		return new DelegatingOAuth2TokenValidator<>(tokenValidators);
	}

	/**
	 * 基于 Nimbus 的 jwt 解码器，并增加了一些自定义校验策略
	 *
	 * @param validator DelegatingOAuth2TokenValidator<Jwt>
	 * @return jwt decoder
	 */
	@Bean
	@SneakyThrows
	JwtDecoder jwtDecoder(@Qualifier("delegatingTokenValidator") DelegatingOAuth2TokenValidator<Jwt> validator) {
		// 指定 X.509 类型的证书工厂
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		// 读取并构建证书
		X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(new FileSystemResource(keyLocation + "/demoKey.cer").getInputStream());
		// 解析证书
		RSAKey rsaKey = RSAKey.parse(certificate);
		// 得到公钥
		RSAPublicKey key = rsaKey.toRSAPublicKey();

		// 构造解码器
		NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withPublicKey(key).build();
		nimbusJwtDecoder.setJwtValidator(validator); // 注入自定义 JWT 校验逻辑
		return nimbusJwtDecoder;
	}

}
