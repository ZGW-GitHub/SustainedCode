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

package com.code.spring.oauth.mode.code.server.component;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2023/7/11 14:04
 */
@Slf4j
@Component
public class RedisSecurityContextRepository implements SecurityContextRepository {

	public static final String  SPRING_SECURITY_AUTH_HEADER = "nonce";
	public static final String  SPRING_SECURITY_CONTEXT_KEY = "security:context:%s";
	public static final Integer DEFAULT_TIMEOUT_SECONDS     = 5 * 60 * 1000;

	private final String defaultNonce = "666";

	@Resource
	private RedissonClient redissonClient;

	private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

	@Override
	@SuppressWarnings("all")
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		log.debug("【 SecurityContextRepository 】获取 context");

		return readSecurityContextFromRedis(requestResponseHolder.getRequest());
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		log.debug("【 SecurityContextRepository 】保存 context");

		String nonce = request.getHeader(SPRING_SECURITY_AUTH_HEADER);
		if (ObjectUtils.isEmpty(nonce)) {
			// TODO
			nonce = defaultNonce;
			// return;
		}

		// 如果当前的 context 是空的，则移除
		SecurityContext emptyContext = this.securityContextHolderStrategy.createEmptyContext();
		if (emptyContext.equals(context)) {
			redissonClient.getBucket(getSpringSecurityContextKey(nonce)).delete();
		} else {
			// 保存认证信息
			RBucket<SecurityContext> bucket = redissonClient.getBucket(getSpringSecurityContextKey(nonce), new SerializationCodec());
			bucket.set(context, DEFAULT_TIMEOUT_SECONDS, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		log.debug("【 SecurityContextRepository 】是否存在 context");

		String nonce = request.getHeader(SPRING_SECURITY_AUTH_HEADER);
		if (ObjectUtils.isEmpty(nonce)) {
			// TODO
			nonce = defaultNonce;
			// return false;
		}

		// 检验当前请求是否有认证信息
		return redissonClient.getBucket(getSpringSecurityContextKey(nonce)).isExists();
	}

	/**
	 * 从 redis 中获取认证信息
	 *
	 * @param request 当前请求
	 * @return 认证信息
	 */
	private SecurityContext readSecurityContextFromRedis(HttpServletRequest request) {
		if (request == null) {
			return null;
		}

		String nonce = request.getHeader(SPRING_SECURITY_AUTH_HEADER);
		if (ObjectUtils.isEmpty(nonce)) {
			// TODO
			nonce = defaultNonce;
			// return null;
		}

		// 根据缓存 id 获取认证信息
		RBucket<SecurityContext> bucket = redissonClient.getBucket(getSpringSecurityContextKey(nonce), new SerializationCodec());
		if (!bucket.isExists()) {
			return null;
		}

		return bucket.get();
	}

	private String getSpringSecurityContextKey(String nonce) {
		return SPRING_SECURITY_CONTEXT_KEY.formatted(nonce);
	}

}
