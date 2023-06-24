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

package com.code.framework.basic.exception.code;

import com.code.framework.basic.exception.BizException;
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
public enum BizExceptionCode implements ExceptionCode<BizException> {

	/**
	 * 通用错误码
	 */
	COMMON_ERROR(400, "服务器繁忙，请稍后重试"),

	/**
	 * RuntimeException
	 */
	RUNTIME_EXCEPTION(401, "服务器繁忙，请稍后重试"),

	NESTED_RUNTIME_EXCEPTION(110000, "嵌套 RuntimeException"),

	/**
	 * 系统异常
	 */
	LIMIT_ERROR(110001, "访问过于频繁，请稍后再试"),
	ILLEGAL_REQUEST(110002, "非法请求，请重新刷新页面操作"),

	/**
	 * trace 异常
	 */
	TRACE_EXCEPTION(111001, "Trace 异常"),
	TRACE_CONTEXT_EXIST_EXCEPTION(111002, "当前线程已有 TraceContext ，不能新建 TraceContext"),
	TRACE_CONTEXT_NOT_EXIST_EXCEPTION(111002, "当前线程没有 TraceContext"),

	/**
	 * Util 异常
	 */
	UTIL_BEAN_MAP_EXCEPTION(112001, "BeanUtil#map 异常，source 为 null"),

	/**
	 * 参数异常
	 */
	PARAMS_ERROR(4001, "参数异常"),
	VALIDATION_EXCEPTION(4002, "验证异常"),

	/**
	 * 定时任务
	 */
	BAD_XXL_JOB_HANDLER(2001, "定时任务配置错误");

	private final int    code;
	private final String message;

	@Override
	public Supplier<BizException> exceptionSupplier() {
		return BizException::new;
	}

}
