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

package com.code.framework.web.controller.domain;

import com.code.framework.basic.exception.core.Exception;
import com.code.framework.basic.exception.core.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author 愆凡
 * @date 2022/6/13 22:07
 */
@Slf4j
@Getter
public class GatewayResponse<T> implements Serializable {

	public static final int    SUCCESS_CODE = 200;
	public static final String SUCCESS_MSG  = "成功";

	/**
	 * 返回码
	 */
	private Integer code;

	/**
	 * 返回数据
	 */
	private T data;

	/**
	 * 错误提示
	 */
	private String message;

	private GatewayResponse() {
	}

	public static GatewayResponse<String> success() {
		return success("");
	}

	public static <T> GatewayResponse<T> success(T data) {
		GatewayResponse<T> result = new GatewayResponse<>();
		result.code = SUCCESS_CODE;
		result.message = SUCCESS_MSG;
		result.data = data;
		return result;
	}

	public static <T> GatewayResponse<T> error(ExceptionCode exceptionCode) {
		return error(exceptionCode, exceptionCode.getMessage());
	}

	private static <T> GatewayResponse<T> error(ExceptionCode exceptionCode, String message) {
		GatewayResponse<T> result = new GatewayResponse<>();
		result.code = exceptionCode.getCode();
		result.message = message;
		return result;
	}

	public static <T> GatewayResponse<T> error(Exception exception) {
		return error(exception, exception.getMessage());
	}

	private static <T> GatewayResponse<T> error(Exception exception, String message) {
		GatewayResponse<T> result = new GatewayResponse<>();
		result.code = exception.getCode();
		result.message = message;
		return result;
	}

}
