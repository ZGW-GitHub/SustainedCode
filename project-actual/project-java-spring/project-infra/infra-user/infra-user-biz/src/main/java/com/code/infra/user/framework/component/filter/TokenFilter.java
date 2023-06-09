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

package com.code.infra.user.framework.component.filter;

import cn.hutool.core.util.StrUtil;
import com.code.infra.user.framework.config.SecurityConfig;
import com.code.infra.user.mvc.service.UserInfoService;
import com.code.infra.user.mvc.service.domain.CurrentUserInfoBO;
import com.code.infra.user.mvc.service.domain.CurrentUserInfoDTO;
import com.code.infra.user.pojo.TokenInfoPOJO;
import com.code.infra.user.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * @author Snow
 * @date 2023/7/1 21:39
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

	private final UserInfoService userInfoService;

	private final SecurityConfig securityConfig;

	public TokenFilter(UserInfoService userInfoService, SecurityConfig securityConfig) {
		this.userInfoService = userInfoService;
		this.securityConfig = securityConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (isWhiteListResource(request)) {
			log.debug("【 TokenFilter 】该请求[{}]无需 token", request.getServletPath());
			filterChain.doFilter(request, response);
			return;
		}

		// 从请求头中获取鉴权 token
		final String token = request.getHeader("Authorization");

		// 如果不存在 Token ，则继续执行过滤器链，过滤器链会返回重定向登录页
		if (StrUtil.isBlank(token)) {
			log.debug("【 TokenFilter 】token 为空");
			filterChain.doFilter(request, response);
			return;
		}

		TokenInfoPOJO tokenInfoPOJO = JWTUtil.extractToken(token);
		if (!tokenInfoPOJO.isValid()) {
			log.debug("【 TokenFilter 】token 非法");
			writeResponse(request, response);
			return;
		}

		String account = tokenInfoPOJO.subject();

		// SecurityContextHolder 中的 Authentication 为空时，才进行处理
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			// 获取用户信息 TODO 放入 redis 缓存，减少 mysql 压力（注意：mysql 、redis 数据一致性）
			// TODO 从 redis 中查询 token 是否存在（ 以判断是不是历史且未过期的 token ），格式：key: account 、value(hash): token:xxx,userInfo:xxx

			CurrentUserInfoDTO currentUserInfoDTO = userInfoService.findCurrentUserInfo(new CurrentUserInfoBO().setAccount(account));
			currentUserInfoDTO.setToken(token);

			// 如果 token 有效，将用户信息存储到 SecurityContextHolder，方便后续使用
			List<SimpleGrantedAuthority> grantedAuthorityList = currentUserInfoDTO.getGrantedAuthority().stream().map(SimpleGrantedAuthority::new).toList();
			UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(account, null, grantedAuthorityList);
			authentication.setDetails(currentUserInfoDTO);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			log.debug("【 TokenFilter 】SecurityContextHolder Context 为空. 设置 Context : {}", authentication);
		} else {
			log.warn("【 TokenFilter 】SecurityContextHolder Context 不为空. token : {}, Context : {}", token, SecurityContextHolder.getContext().getAuthentication());
		}

		filterChain.doFilter(request, response);
	}

	private boolean isWhiteListResource(HttpServletRequest request) {
		final List<String> whiteList = securityConfig.getWhiteList();
		for (String white : whiteList) {
			if (new AntPathRequestMatcher(white).matcher(request).isMatch()) {
				return true;
			}
		}
		return false;
	}

	private static void writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, String> map = new HashMap<>(2);
		map.put("uri", request.getRequestURI());
		map.put("msg", "拒绝访问");
		String resBody = new ObjectMapper().writeValueAsString(map);

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding("utf-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		PrintWriter printWriter = response.getWriter();
		printWriter.print(resBody);
		printWriter.flush();
		printWriter.close();
	}

}
