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

package com.code.framework.web.configuration;

import com.code.framework.web.component.interceptor.LogHandlerInterceptor;
import com.code.framework.web.component.interceptor.TraceHandlerInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Snow
 * @date 2023/6/13 20:31
 */
@Slf4j
@Configuration
public class HandlerInterceptorConfiguration implements WebMvcConfigurer {

	@Resource
	private TraceHandlerInterceptor traceHandlerInterceptor;

	@Resource
	private LogHandlerInterceptor logHandlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(traceHandlerInterceptor);
		registry.addInterceptor(logHandlerInterceptor);
	}

}
