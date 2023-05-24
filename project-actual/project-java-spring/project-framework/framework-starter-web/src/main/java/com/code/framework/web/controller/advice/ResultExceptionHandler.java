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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 愆凡
 * @date 2022/6/13 22:02
 */
@Slf4j
@Component
@RestControllerAdvice
public class ResultExceptionHandler {

	/**
	 * 异常处理程序
	 *
	 * @param request   请求
	 * @param response  响应
	 * @param throwable 异常
	 * @return {@link GatewayResponse}<{@link Void}>
	 */
	@ExceptionHandler(Throwable.class)
	public GatewayResponse<Void> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
		log.error("【 异常拦截 】>>>>>> 异常类型：{}", throwable.getClass());

		if (throwable instanceof ConstraintViolationException constraintViolationException) {
			// 处理验证异常
			return handleConstraintViolationException(constraintViolationException);
		}

		if (throwable instanceof Exception customRuntimeException) {
			// 处理自定义 RuntimeException
			return handleCustomRuntimeException(customRuntimeException);
		}

		if (throwable instanceof java.lang.Exception exception) {
			// 处理 java.lang.Exception
			return handleException(request, response, exception);
		}

		// 处理 java.lang.Throwable
		return handleThrowable(request, response, throwable);
	}

	private GatewayResponse<Void> handleConstraintViolationException(ConstraintViolationException exception) {
		log.error("【 异常拦截 】>>>>>> ValidationException : {}", exception.getMessage(), exception);

		StringBuilder violationMessage = StrUtil.builder();
		exception.getConstraintViolations().forEach(constraintViolation -> buildViolationMessage(violationMessage, constraintViolation));

		if (violationMessage.isEmpty()) {
			violationMessage.append(exception.getMessage());
		} else {
			violationMessage.delete(violationMessage.length() - 2, violationMessage.length());
		}
		return GatewayResponse.error(BizExceptionCode.VALIDATION_EXCEPTION.exception(violationMessage.toString()));
	}

	private GatewayResponse<Void> handleCustomRuntimeException(Exception exception) {
		log.error("【 异常拦截 】>>>>>> ServiceException : {}-{}", exception.getCode(), exception.getMessage(), exception);

		return GatewayResponse.error(exception);
	}

	private GatewayResponse<Void> handleException(HttpServletRequest request, HttpServletResponse response, java.lang.Exception exception) {
		log.error("【 异常拦截 】>>>>>> Exception : {}", exception.getMessage(), exception);

		return GatewayResponse.error(BizExceptionCode.COMMON_ERROR);
	}

	private GatewayResponse<Void> handleThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
		log.error("【 异常拦截 】>>>>>> Throwable : {}", throwable.getMessage(), throwable);

		return GatewayResponse.error(BizExceptionCode.COMMON_ERROR);
	}

	private void buildViolationMessage(StringBuilder violationMessage, ConstraintViolation<?> constraintViolation) {
		if (!(constraintViolation instanceof ConstraintViolationImpl<?> violation)) {
			return;
		}

		Class<?> constraintClazz = violation.getLeafBean().getClass();
		violationMessage.append(constraintClazz.getSimpleName()).append(StrUtil.SPACE);
		Path propertyPath = violation.getPropertyPath();
		if (propertyPath instanceof PathImpl path) {
			String propertyName = path.getLeafNode().getName();
			violationMessage.append("的 ").append(propertyName).append(" : ");
		}
		String violationMsg = violation.getMessage();
		violationMessage.append(violationMsg).append(", ");
	}

}
