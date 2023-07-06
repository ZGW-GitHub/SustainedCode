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

package com.code.infra.user.pojo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2023/7/3 20:36
 */
@Slf4j
@Data
@Accessors(chain = true)
public class TokenInfoPOJO {

	private JSONObject payloadJson;

	private Boolean refreshToken;

	public String subject() {
		return payloadJson.getStr(Claims.SUBJECT, StrUtil.EMPTY);
	}

	/**
	 * 验证 Token 是否有效
	 *
	 * @return boolean
	 */
	public boolean isValid() {
		// TODO 检查 token 在 redis 中是否存在
		if (refreshToken) {
			return false;
		}

		return StrUtil.isNotBlank(subject()) && !isExpired();
	}

	/**
	 * 判断 Token 是否过期
	 */
	private boolean isExpired() {
		long expiration = payloadJson.getLong(Claims.EXPIRATION, Long.MIN_VALUE);
		return System.currentTimeMillis() > expiration;
	}

}
