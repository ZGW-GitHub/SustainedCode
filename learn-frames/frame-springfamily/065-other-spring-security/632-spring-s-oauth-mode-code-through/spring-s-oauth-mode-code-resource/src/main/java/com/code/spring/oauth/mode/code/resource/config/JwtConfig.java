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

package com.code.spring.oauth.mode.code.resource.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Snow
 * @date 2023/6/4 15:50
 */
@Slf4j
@Data
@Configuration(proxyBeanMethods = false)
public class JwtConfig {

	/**
	 * jwt 有效期
	 */
	@Value("${spring.jwt.custom.expires:0}")
	private Long expires;

	@Value("${spring.jwt.custom.keystore.public-key-location}")
	private String publicKeyLocation;

}
