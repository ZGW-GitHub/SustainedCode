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

package com.code.framework.basic.result;

import com.code.framework.basic.exception.BizException;
import com.code.framework.basic.exception.BizExceptionCode;
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

	@ExceptionHandler(Exception.class)
	public CommonResult<?> bottomExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		log.error("[异常拦截 : RuntimeException]", exception);

		return error(BizExceptionCode.COMMON_ERROR);
	}

	@ExceptionHandler(BizException.class)
	public CommonResult<?> bizExceptionhandler(BizException bizException) {
		log.error("[异常拦截 : ServiceException] : {}-{}", bizException.getCode(), bizException.getMessage(), bizException);

		return CommonResult.error(bizException);
	}

}
