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

package com.code.framework.web.component.filter;

import cn.hutool.core.util.StrUtil;
import com.code.framework.basic.trace.context.TraceContextHelper;
import com.code.framework.basic.trace.context.TraceContextKeyEnum;
import com.code.framework.basic.util.IdGenerator;
import com.code.framework.basic.util.MDCUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Snow
 * @date 2023/7/1 21:48
 */
@Slf4j
@Component
public class TraceFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 1、生成/获取 traceId
		String traceId = request.getParameter(TraceContextKeyEnum.TRACE_ID.getName());
		if (StrUtil.isBlank(traceId)) {
			traceId = IdGenerator.traceId();
		}

		// 2、为日志设置 traceId
		MDCUtil.setTraceId(traceId);

		// 3、创建 TraceContext 并设置 traceId
		TraceContextHelper.startTrace(traceId);

		// 4、继续执行 Filter 链
		filterChain.doFilter(request, response);

		// 5、清除 traceId
		MDCUtil.removeTraceId();
	}

}
