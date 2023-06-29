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

package com.code.spring.security.jwt;

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
public class JwtService {

	/**
	 * 加密盐值
	 */
	private final String secretKey = "demo";

	/**
	 * Token 失效时间
	 */
	private final long jwtExpiration = 2 * 60 * 60 * 1000;

	/**
	 * Token 刷新时间
	 */
	private final long refreshExpiration = 24 * 60 * 60 * 1000;

	/**
	 * 从 Token 中获取 Username
	 *
	 * @param token Token
	 * @return String
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * 从 Token 中获取数据
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * 生成 Token ，仅包含用户信息，无其它额外信息
	 *
	 * @param sysUser 系统用户
	 * @return {@link String}
	 */
	public String generateToken(SysUser sysUser) {
		return generateToken(new HashMap<>(), sysUser);
	}

	/**
	 * 生成 Token ，有额外信息
	 *
	 * @param extraClaims 额外的数据
	 * @param sysUser     系统用户
	 * @return String
	 */
	public String generateToken(Map<String, Object> extraClaims, SysUser sysUser) {
		return buildToken(extraClaims, sysUser, jwtExpiration);
	}

	/**
	 * 生成 RefreshToken
	 *
	 * @param sysUser 用户信息
	 * @return String
	 */
	public String generateRefreshToken(SysUser sysUser) {
		return buildToken(new HashMap<>(), sysUser, refreshExpiration);
	}

	/**
	 * 构建 Token
	 *
	 * @param extraClaims 额外信息
	 * @param expiration  失效时间
	 * @param sysUser     系统用户
	 * @return String
	 */
	private String buildToken(Map<String, Object> extraClaims, SysUser sysUser, long expiration) {
		return Jwts
				.builder()
				.setClaims(extraClaims) // body
				.setSubject(sysUser.getUsername()) // 主体数据
				.setIssuedAt(new Date(System.currentTimeMillis())) // 设置发布时间
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // 设置过期时间
				.signWith(getSignInKey(), SignatureAlgorithm.HS256) // 设置摘要算法
				.compact();
	}

	/**
	 * 验证 Token 是否有效
	 *
	 * @param token   Token
	 * @param sysUser 用户信息
	 * @return boolean
	 */
	public boolean isTokenValid(String token, SysUser sysUser) {
		final String username = extractUsername(token);
		return (username.equals(sysUser.getUsername())) && !isTokenExpired(token);
	}

	/**
	 * 判断 Token 是否过期
	 */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * 从 Token 中获取失效时间
	 */
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * 从 Token 中获取所有数据
	 */
	private Claims extractAllClaims(String token) {
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
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
