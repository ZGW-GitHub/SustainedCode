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

import com.code.spring.oauth.mode.code.client.config.JwtConfig;
import com.code.spring.oauth.mode.code.client.config.OAuthConfig;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
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

	@Resource
	private JwtConfig jwtConfig;

	@Resource
	private OAuthConfig oAuthConfig;

	/**
	 * 校验 jwt issuer 是否合法
	 *
	 * @return jwt issuer validator
	 */
	@Bean
	JwtIssuerValidator jwtIssuerValidator() {
		return new JwtIssuerValidator(oAuthConfig.getIssuer());
	}

	/**
	 * 校验 jwt 是否过期
	 *
	 * @return jwt timestamp validator
	 */
	@Bean
	JwtTimestampValidator jwtTimestampValidator() {
		// 入参：为 0 时，和令牌实际时间一致。大于 0 时，会在原来过期时间的基础再加上入参值。所以这里可以不用配置这个值。
		return new JwtTimestampValidator(Duration.ofSeconds(jwtConfig.getExpires()));
	}

	/**
	 * JWT 个性化解析
	 *
	 * @return JWT 认证转换器
	 */
	// @Bean("customJwtAuthenticationConverter")
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		// 如果不按照规范解析权限集合 Authorities 就需要自定义 key
		// jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scopes");
		// OAuth2 默认前缀是 "SCOPE_" 、Spring Security 是 "ROLE_"
		// jwtGrantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		jwtAuthenticationConverter.setPrincipalClaimName(JwtClaimNames.SUB);
		return jwtAuthenticationConverter;
	}

	/**
	 * 基于 Nimbus 的 jwt 解码器，并增加了一些自定义校验策略
	 *
	 * @param oAuth2TokenValidators Collection<OAuth2TokenValidator<Jwt>>
	 *
	 * @return jwt decoder
	 */
	@Bean("customJwtDecoder")
	JwtDecoder jwtDecoder(Collection<OAuth2TokenValidator<Jwt>> oAuth2TokenValidators) throws JOSEException, CertificateException, IOException {
		// 指定 X.509 类型的证书工厂
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		// 读取并构建证书
		Certificate certificate = certificateFactory.generateCertificate(new FileSystemResource(jwtConfig.getPublicKeyLocation()).getInputStream());
		// 解析证书
		RSAKey rsaKey = RSAKey.parse((X509Certificate) certificate);
		// 得到公钥
		RSAPublicKey key = rsaKey.toRSAPublicKey();

		// 构造解码器
		NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withPublicKey(key).build();
		nimbusJwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(oAuth2TokenValidators)); // 注入自定义 JWT 校验逻辑
		return nimbusJwtDecoder;
	}

}
