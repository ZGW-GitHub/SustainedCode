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

package com.code.xxljob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Snow
 * @date 2022/4/21 11:36
 */
@Slf4j
@Configuration
public class XxlJobConfig {

	@Value("${xxl.job.admin.addresses}")
	private String adminAddress;

	@Bean
	@ConfigurationProperties("xxl.job.executor")
	public XxlJobSpringExecutor xxlJobExecutor() {
		XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
		executor.setAdminAddresses(adminAddress);
		return executor;
	}

}
