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

package com.code.infra.user.framework.component.security;

import cn.hutool.core.util.StrUtil;
import com.code.framework.basic.util.PasswordUtil;
import com.code.infra.user.framework.exception.UserExceptionCode;
import com.code.infra.user.mvc.service.domain.UserAuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Snow
 * @date 2023/7/1 18:12
 */
@Slf4j
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		super.setPasswordEncoder(passwordEncoder);
		super.setUserDetailsService(userDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication, () -> this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports", "Only UsernamePasswordAuthenticationToken is supported"));
		if (!(authentication instanceof UsernamePasswordAuthenticationToken upAuthentication)) {
			throw new AuthenticationServiceException("Authentication 类型错误");
		}

		String username = determineUsername(authentication);

		// 校验验证码
		validationCaptcha(username);

		boolean cacheWasUsed = true;
		UserDetails user = getUserCache().getUserFromCache(username);
		if (user == null) {
			cacheWasUsed = false;
			try {
				user = retrieveUser(username, upAuthentication);
			} catch (UsernameNotFoundException ex) {
				log.debug("Failed to find user '" + username + "'");

				if (!this.hideUserNotFoundExceptions) {
					throw ex;
				}
				throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}

			Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		}

		// 对 authentication 中的密码进行编码
		upAuthentication = encodeAuthenticationRequestPassword(upAuthentication.getCredentials().toString(), user, username);

		try {
			getPreAuthenticationChecks().check(user);
			additionalAuthenticationChecks(user, upAuthentication);
		} catch (AuthenticationException ex) {
			if (!cacheWasUsed) {
				throw ex;
			}

			// There was a problem, so try again after checking we're using latest data (i.e. not from the cache)
			cacheWasUsed = false;
			user = retrieveUser(username, upAuthentication);

			// 对 authentication 中的密码进行编码
			upAuthentication = encodeAuthenticationRequestPassword(upAuthentication.getCredentials().toString(), user, username);

			getPreAuthenticationChecks().check(user);
			additionalAuthenticationChecks(user, upAuthentication);
		}

		getPostAuthenticationChecks().check(user);

		if (!cacheWasUsed) {
			getUserCache().putUserInCache(user);
		}

		Object principalToReturn = user;
		if (isForcePrincipalAsString()) {
			principalToReturn = user.getUsername();
		}

		return createSuccessAuthentication(principalToReturn, authentication, user);
	}

	private void validationCaptcha(String username) {
		// 获取当前request
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			throw UserExceptionCode.CAPTCHA_VALIDATION_EXCEPTION.exception("无法获取当前 request");
		}
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		// 获取参数中的验证码
		String captcha = request.getParameter("captcha");
		if (StrUtil.isEmpty(captcha)) {
			throw UserExceptionCode.CAPTCHA_VALIDATION_EXCEPTION.exception("验证码为空");
		}

		// TODO 获取 redis 缓存的验证码
		String captchaCache = username + "cache";
		if (!StrUtil.equals(captchaCache, captcha)) {
			throw UserExceptionCode.CAPTCHA_INCORRECT_INPUT.exception();
		}
	}

	private static UsernamePasswordAuthenticationToken encodeAuthenticationRequestPassword(String presentedPassword, UserDetails user, String username) {
		if (!(user instanceof UserAuthDTO userAuthDTO)) {
			throw new AuthenticationServiceException("UserDetails 类型错误");
		}

		return UsernamePasswordAuthenticationToken.unauthenticated(username, PasswordUtil.encode(presentedPassword, userAuthDTO.getSalt()));
	}

	private String determineUsername(Authentication authentication) {
		return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
	}

}
