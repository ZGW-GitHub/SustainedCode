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

package com.code.framework.basic.result.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 愆凡
 * @date 2022/6/13 21:51
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum implements ResultCode {

	/**
	 * 处理成功
	 */
	SUCCESS(200, "成功"),

	/**
	 * 通用错误码
	 */
	COMMON_ERROR(400, "服务器繁忙，请稍后重试"),

	/**
	 * RuntimeException
	 */
	RUNTIME_EXCEPTION(401, "服务器繁忙，请稍后重试"),

	/**
	 * 参数异常
	 */
	PARAMS_ERROR(4001, "参数异常"),

	/**
	 * 系统异常
	 */
	LIMIT_ERROR(1001, "访问过于频繁，请稍后再试"),
	ILLEGAL_REQUEST(1002, "非法请求，请重新刷新页面操作"),

	/**
	 * 定时任务
	 */
	BAD_XXL_JOB_HANDLER(2001, "定时任务配置错误");

	private final int     code;
	private final String  message;

}
