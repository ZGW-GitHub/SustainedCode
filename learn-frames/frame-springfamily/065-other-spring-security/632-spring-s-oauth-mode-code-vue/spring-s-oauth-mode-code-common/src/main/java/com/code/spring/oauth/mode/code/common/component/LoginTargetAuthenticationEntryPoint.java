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

package com.code.spring.oauth.mode.code.common.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Snow
 * @date 2023/7/11 14:01
 */
@Slf4j
public class LoginTargetAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public LoginTargetAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		// 获取登录表单的地址
		String loginForm = determineUrlToUseForThisRequest(request, response, authException);
		if (!UrlUtils.isAbsoluteUrl(loginForm)) {
			// 不是绝对路径调用父类方法处理
			super.commence(request, response, authException);
			return;
		}

		StringBuffer requestUrl = request.getRequestURL();
		if (!ObjectUtils.isEmpty(request.getQueryString())) {
			requestUrl.append("?").append(request.getQueryString());
		}

		// 绝对路径在重定向前添加 target 参数
		String targetUrl = URLEncoder.encode(requestUrl.toString(), StandardCharsets.UTF_8);
		this.redirectStrategy.sendRedirect(request, response, (loginForm + "?target=" + targetUrl));

	}

}
