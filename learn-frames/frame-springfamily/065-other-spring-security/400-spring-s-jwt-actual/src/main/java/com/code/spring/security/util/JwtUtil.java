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

package com.code.spring.security.util;

import com.code.spring.security.dal.dos.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Snow
 * @date 2023/6/29 10:41
 */
@Slf4j
public class JwtUtil {

	/**
	 * 加密盐值
	 */
	private static final String SECRET_KEY = "demo";

	/**
	 * Token 失效时间
	 */
	private static final long TOKEN_EXPIRATION = 2 * 60 * 60 * 1000;

	/**
	 * Token 刷新时间
	 */
	private static final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;

	private static final String IS_REFRESH_TOKEN = "isRefreshToken";

	/**
	 * 从 Token 中获取 Username
	 *
	 * @param token Token
	 *
	 * @return String
	 */
	public static String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * 从 Token 中获取数据
	 */
	public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * 生成 Token ，仅包含用户信息，无其它额外信息
	 *
	 * @param sysUser 系统用户
	 *
	 * @return {@link String}
	 */
	public static String generateToken(SysUser sysUser) {
		return generateToken(sysUser, new HashMap<>());
	}

	/**
	 * 生成 Token ，有额外信息
	 *
	 * @param extraClaims 额外的数据
	 * @param sysUser     系统用户
	 *
	 * @return String
	 */
	public static String generateToken(SysUser sysUser, Map<String, Object> extraClaims) {
		return buildToken(sysUser, extraClaims, TOKEN_EXPIRATION);
	}

	/**
	 * 生成 RefreshToken
	 *
	 * @param sysUser 用户信息
	 *
	 * @return String
	 */
	public static String generateRefreshToken(SysUser sysUser) {
		return buildToken(sysUser, new HashMap<>(), REFRESH_TOKEN_EXPIRATION);
	}

	/**
	 * 构建 Token
	 *
	 * @param extraClaims 额外信息
	 * @param expiration  失效时间
	 * @param sysUser     系统用户
	 *
	 * @return String
	 */
	private static String buildToken(SysUser sysUser, Map<String, Object> extraClaims, long expiration) {
		extraClaims.put(IS_REFRESH_TOKEN, expiration == REFRESH_TOKEN_EXPIRATION);

		// TODO 将生成的 token 存储到 redis

		return Jwts
				.builder()
				.setClaims(extraClaims) // body
				.setSubject(sysUser.getUsername()) // 主体数据
				.setIssuedAt(new Date(System.currentTimeMillis())) // 设置发布时间
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // 设置过期时间
				.signWith(getSignInKey(), SignatureAlgorithm.HS256) // 设置摘要算法
				.compact();
	}

	public static void revokeToken(SysUser sysUser) {
		// TODO 删除 redis 中的 token
	}

	/**
	 * 验证 Token 是否有效
	 *
	 * @param token   Token
	 * @param sysUser 用户信息
	 *
	 * @return boolean
	 */
	public static boolean isTokenValid(String token, SysUser sysUser, Boolean isRefreshToken) {
		// TODO 检查 token 在 redis 中是否存在

		final Claims claims = extractAllClaims(token);
		if (claims.get(IS_REFRESH_TOKEN, Boolean.class) != isRefreshToken) {
			return false;
		}

		final String username = claims.get(Claims.SUBJECT, String.class);
		return (username.equals(sysUser.getUsername())) && !isTokenExpired(token);
	}

	/**
	 * 判断 Token 是否过期
	 */
	private static boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * 从 Token 中获取失效时间
	 */
	private static Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * 从 Token 中获取所有数据
	 */
	private static Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * 获取签名 Key ，Token 加密解密使用
	 */
	private static Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
