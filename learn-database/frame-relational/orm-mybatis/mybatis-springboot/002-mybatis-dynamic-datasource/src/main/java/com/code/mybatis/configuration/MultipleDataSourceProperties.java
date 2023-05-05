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

package com.code.mybatis.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Snow
 * @date 2023/2/15 23:14
 */
@Data
@ConfigurationProperties("jdbc")
public class MultipleDataSourceProperties {

	@Value("${jdbc.first.url}")
	private String firstUrl;
	@Value("${jdbc.first.username}")
	private String firstUsername;
	@Value("${jdbc.first.password}")
	private String firstPassword;
	@Value("${jdbc.first.driver}")
	private String firstDriver;

	@Value("${jdbc.second.url}")
	private String secondUrl;
	@Value("${jdbc.second.username}")
	private String secondUsername;
	@Value("${jdbc.second.password}")
	private String secondPassword;
	@Value("${jdbc.second.driver}")
	private String secondDriver;

}
