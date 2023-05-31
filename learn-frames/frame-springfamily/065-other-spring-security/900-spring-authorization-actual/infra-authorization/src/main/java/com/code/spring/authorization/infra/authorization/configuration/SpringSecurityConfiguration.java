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

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Snow
 * @date 2023/5/30 22:00
 */
@Slf4j
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SpringSecurityConfiguration {

	@Value("${keystore.path}")
	private String keystorePath;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers("/**").authenticated())
				.formLogin(withDefaults())
				.logout(withDefaults());

		return httpSecurity.build();
	}

	/**
	 * 模拟用户
	 */
	@Bean
	UserDetailsService users() {
		UserDetails user = User.builder()
				.username("123456")
				.password("123456")
				.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
				.roles("USER")
				//.authorities("SCOPE_userinfo")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

	/**
	 * jwt 解码器：客户端认证授权后，需要访问 user 信息，解码器可以从令牌中解析出 user 信息
	 */
	@Bean
	@SneakyThrows
	JwtDecoder jwtDecoder() {
		String keyFilePath = keystorePath + "/demoKey.cer";

		// 读取 cer 公钥证书来配置解码器
		CertificateFactory certificateFactory = CertificateFactory.getInstance("x.509");
		Certificate certificate = certificateFactory.generateCertificate(new FileSystemResource(keyFilePath).getInputStream());
		RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
		return NimbusJwtDecoder.withPublicKey(publicKey).build();
	}

}
