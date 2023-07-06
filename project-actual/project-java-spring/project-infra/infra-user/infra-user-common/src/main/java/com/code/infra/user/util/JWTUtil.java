/*
 * Copyright (C) <2023> <Snow>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General public static License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General public static License for more details.
 *
 * You should have received a copy of the GNU General public static License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.code.infra.user.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import com.code.infra.user.pojo.TokenInfoPOJO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

/**
 * @author Snow
 * @date 2023/6/29 10:41
 */
@Slf4j
public class JWTUtil {

	/**
	 * 加密盐值
	 */
	private static final String SECRET_KEY = "demodemodemodemodemodemodemodemodemodemodemodemodemodemodemodemo";

	/**
	 * Token 失效时间：2 小时
	 */
	private static final long TOKEN_EXPIRATION = 2 * 60 * 60 * 1000;

	/**
	 * Token 刷新时间：24 小时
	 */
	private static final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;

	/**
	 * 生成 Token ，仅包含用户信息，无其它额外信息
	 *
	 * @param account 用户账号
	 * @return {@link String}
	 */
	public static String generateToken(TokenInfoPOJO tokenInfoPOJO, String account) {
		return buildToken(tokenInfoPOJO, account, TOKEN_EXPIRATION);
	}

	/**
	 * 生成 RefreshToken
	 *
	 * @param account 用户账号
	 * @return String
	 */
	public static String generateRefreshToken(TokenInfoPOJO tokenInfoPOJO, String account) {
		return buildToken(tokenInfoPOJO, account, REFRESH_TOKEN_EXPIRATION);
	}

	/**
	 * 构建 Token
	 *
	 * @param account     用户账号
	 * @param expiration  失效时间
	 * @return String
	 */
	private static String buildToken(TokenInfoPOJO tokenInfoPOJO, String account, long expiration) {
		// TODO 将生成的 token 存储到 redis

		tokenInfoPOJO.setRefreshToken(REFRESH_TOKEN_EXPIRATION == expiration);

		return Jwts
				.builder()
				.setClaims(BeanUtil.beanToMap(tokenInfoPOJO)) // body
				.setSubject(account) // 主体数据
				.setIssuedAt(new Date(System.currentTimeMillis())) // 设置发布时间
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // 设置过期时间
				.signWith(getSignInKey(), SignatureAlgorithm.HS256) // 设置摘要算法
				.compact();
	}

	/**
	 * 解析 Token
	 */
	public static TokenInfoPOJO extractToken(String token) {
		JSONObject payloadJson = JWT.of(token).setKey(getSignInKey().getEncoded()).getPayloads();
		// log.warn("【 JWTUtil 】token 非法：{}", token);
		// log.debug("【 JWTUtil 】token 过期：{}", token);

		TokenInfoPOJO tokenInfoPOJO = JSONUtil.toBean(payloadJson, TokenInfoPOJO.class);
		tokenInfoPOJO.setPayloadJson(payloadJson);
		return tokenInfoPOJO;
	}

	public static void revokeToken(String account) {
		// TODO 删除 redis 中的 token
	}

	/**
	 * 获取签名 Key ，Token 加密解密使用
	 */
	private static Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Test
	void test() {
		TokenInfoPOJO tokenInfoPOJO = new TokenInfoPOJO().setRefreshToken(false);
		System.err.println("原始: " + tokenInfoPOJO);
		String token = generateToken(tokenInfoPOJO, "123456");

		TokenInfoPOJO tokenInfo = extractToken(token);
		System.err.println("解析: " + tokenInfo);
	}

}
