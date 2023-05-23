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

import cn.hutool.core.util.StrUtil;
import com.code.framework.basic.exception.BizExceptionCode;
import com.code.framework.basic.exception.core.Exception;
import com.code.framework.web.controller.domain.GatewayResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author 愆凡
 * @date 2022/6/13 22:02
 */
@Slf4j
@Component
@RestControllerAdvice
public class ResultExceptionHandler {

	/**
	 * 如果超过长度，前端交互体验不佳，使用默认错误消息
	 */
	static Integer MAX_LENGTH = 200;

	@ExceptionHandler(ValidationException.class)
	public GatewayResponse<?> bizExceptionhandler(ValidationException exception) {
		log.error("[异常拦截 : ValidationException] : {}-{}", BizExceptionCode.VALIDATION_EXCEPTION.getCode(), exception.getMessage(), exception);

		String[] split = exception.getMessage().split(StrUtil.BACKSLASH + StrUtil.DOT);
		String msg = Arrays.stream(split).skip(2).collect(Collectors.joining());
		return GatewayResponse.error(BizExceptionCode.VALIDATION_EXCEPTION.exception(msg));
	}

	/**
	 * 异常处理程序 - 自定义异常
	 *
	 * @param exception 自定义异常
	 * @return {@link GatewayResponse}<{@link ?}>
	 */
	@ExceptionHandler(Exception.class)
	public GatewayResponse<?> bizExceptionhandler(Exception exception) {
		log.error("[异常拦截 : ServiceException] : {}-{}", exception.getCode(), exception.getMessage(), exception);

		return GatewayResponse.error(exception);
	}

	/**
	 * 异常处理程序 - 兜底
	 *
	 * @param request   请求
	 * @param response  响应
	 * @param exception 异常
	 * @return {@link GatewayResponse}<{@link ?}>
	 */
	@ExceptionHandler(java.lang.Exception.class)
	public GatewayResponse<?> bottomExceptionHandler(HttpServletRequest request, HttpServletResponse response, java.lang.Exception exception) {
		log.error("[异常拦截 : RuntimeException]", exception);

		return GatewayResponse.error(BizExceptionCode.COMMON_ERROR);
	}

}
