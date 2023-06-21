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

package com.code.framework.basic.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @author Snow
 * @date 2023/6/21 10:42
 */
@Slf4j
public class PasswordUtil {

	/**
	 * 用于生成安全随机数
	 */
	private static final SecureRandom secureRandom = new SecureRandom();

	/**
	 * HMAC 摘要算法使用的秘钥
	 */
	private static final String hmacKey = "snow";

	public static String generateSalt() {
		synchronized (secureRandom) {
			byte[] salt = new byte[16];
			secureRandom.nextBytes(salt);
			return HexUtil.encodeHexStr(salt);
		}
	}

	public static String encode(String password, String salt) {
		if (!HexUtil.isHexNumber(salt)) {
			throw new RuntimeException("密码加密 ：salt 非法");
		}

		byte[] saltBytes = HexUtil.decodeHex(salt);
		byte[] passwordBytes = password.getBytes(StandardCharsets.ISO_8859_1);

		byte[] data = new byte[saltBytes.length + passwordBytes.length];
		System.arraycopy(saltBytes, 0, data, 0, saltBytes.length);
		System.arraycopy(passwordBytes, 0, data, saltBytes.length, passwordBytes.length);

		return SecureUtil.hmacSha256(hmacKey).digestHex(data);
	}

	public static boolean check(String publicPassword, String salt, String privatePassword) {
		return privatePassword.equals(encode(publicPassword, salt));
	}

	@Test
	void test() {
		String salt = PasswordUtil.generateSalt();
		String password = PasswordUtil.encode("123456", salt);
		System.err.println(salt);
		System.err.println(password);

		System.err.println("1: " + PasswordUtil.check("1234566", salt, password));
		System.err.println("2: " + PasswordUtil.check("123456", salt, password));
	}

}
