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

import com.code.spring.oauth.mode.code.server.config.JwtConfig;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;

/**
 * @author Snow
 * @date 2023/6/4 15:37
 */
@Slf4j
@Configuration
public class JwtConfiguration {

	@Resource
	private JwtConfig jwtConfig;

	/**
	 * 加载 jwk 资源（用于生成令牌）
	 *
	 * @return JWK Source
	 */
	@Bean
	@SneakyThrows
	public JWKSource<SecurityContext> jwkSource() {
		// keystore 的路径
		String keystorePath = jwtConfig.getKeystoreLocation();
		// keystore 密码
		char[] keystorePassword = jwtConfig.getKeystorePassword().toCharArray();
		// 加载 keystore
		KeyStore jks = KeyStore.getInstance("jks");
		jks.load(new FileSystemResource(keystorePath).getInputStream(), keystorePassword);

		// 加载 RSAKey
		RSAKey rsaKey = RSAKey.load(jks, jwtConfig.getPublicKeyAlias(), keystorePassword);

		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	/**
	 * jwt 解码器：客户端认证授权后，需要访问 user 信息，解码器可以从令牌中解析出 user 信息
	 */
	@Bean
	@SneakyThrows
	JwtDecoder jwtDecoder() {
		// 读取 cer 公钥证书来配置解码器
		CertificateFactory certificateFactory = CertificateFactory.getInstance("x.509");
		Certificate certificate = certificateFactory.generateCertificate(new FileSystemResource(jwtConfig.getPublicKeyLocation()).getInputStream());

		RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();

		return NimbusJwtDecoder.withPublicKey(publicKey).build();
	}

}
