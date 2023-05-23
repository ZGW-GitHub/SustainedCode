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

package com.code.framework.web.controller.advice;

import com.code.framework.basic.exception.BizExceptionCode;
import com.code.framework.basic.result.CommonResult;
import com.code.framework.basic.result.ResultAccessor;
import com.code.framework.basic.result.code.Exception;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 愆凡
 * @date 2022/6/13 22:02
 */
@Slf4j
@RestControllerAdvice
public class ResultExceptionHandler implements ResultAccessor {

	/**
	 * 如果超过长度，前端交互体验不佳，使用默认错误消息
	 */
	static Integer MAX_LENGTH = 200;

	/**
	 * 异常处理程序 - 兜底
	 *
	 * @param request   请求
	 * @param response  响应
	 * @param exception 异常
	 * @return {@link CommonResult}<{@link ?}>
	 */
	@ExceptionHandler(java.lang.Exception.class)
	public CommonResult<?> bottomExceptionHandler(HttpServletRequest request, HttpServletResponse response, java.lang.Exception exception) {
		log.error("[异常拦截 : RuntimeException]", exception);

		return error(BizExceptionCode.COMMON_ERROR);
	}

	/**
	 * 异常处理程序 - 自定义异常
	 *
	 * @param exception 自定义异常
	 * @return {@link CommonResult}<{@link ?}>
	 */
	@ExceptionHandler(Exception.class)
	public CommonResult<?> bizExceptionhandler(Exception exception) {
		log.error("[异常拦截 : ServiceException] : {}-{}", exception.getCode(), exception.getMessage(), exception);

		return CommonResult.error(exception);
	}

}
