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

package com.code.infra.user.framework.exception;

import com.code.framework.basic.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * Code 规范：<br/>
 * <ul>
 *     <li>均为 6 位数字</li>
 *     <li>BizExceptionCode 	: 11xxxx</li>
 *     <li>ApiExceptionCode 	: 12xxxx</li>
 *     <li>UserExceptionCode	: 13xxxx</li>
 * </ul>
 *
 * @author 愆凡
 * @date 2022/6/13 21:51
 */
@Getter
@AllArgsConstructor
public enum UserExceptionCode implements ExceptionCode<UserException> {

	USER_NOT_EXIST(130001, "用户不存在"),
	USER_EXIST(130001, "用户已存在"),
	USER_ACCOUNT_OR_PASSWORD_INCORRECT(130003, "账号或密码错误"),
	;

	private final int    code;
	private final String message;

	@Override
	public Supplier<UserException> exceptionSupplier() {
		return UserException::new;
	}

}
