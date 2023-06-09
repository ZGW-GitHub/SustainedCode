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

package com.code.spring.oauth.mode.code.resource.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 自定义认证失败处理器
 *
 * @author Snow
 * @date 2023/5/31 20:17
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	@SneakyThrows
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
		if (authException instanceof InvalidBearerTokenException) {
			// TODO token 无效的处理逻辑
			log.warn("【 自定义认证失败处理器 】token 无效");
		}

		if (response.isCommitted()) {
			log.debug("【 自定义认证失败处理器 】认证通过");
			return;
		}

		// TODO 认证失败的处理逻辑
		HashMap<String, String> map = new HashMap<>(2);
		map.put("uri", request.getRequestURI());
		map.put("msg", "认证失败");
		String resBody = new ObjectMapper().writeValueAsString(map);

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		response.setCharacterEncoding("utf-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		PrintWriter printWriter = response.getWriter();
		printWriter.print(resBody);
		printWriter.flush();
		printWriter.close();
	}

}