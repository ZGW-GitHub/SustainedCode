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

package com.code.framework.xxl.job.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Snow
 * @date 2023/6/9 23:31
 */
@Slf4j
@Data
@Configuration
public class XxlJobExecutorConfig {

	@Value("${xxl-job.executor.appname}")
	private String  appname;
	@Value("${xxl-job.executor.port}")
	private Integer port;
	@Value("${xxl-job.executor.log-path}")
	private String  logPath;
	@Value("${xxl-job.executor.log-retention-days}")
	private Integer logRetentionDays;

}
