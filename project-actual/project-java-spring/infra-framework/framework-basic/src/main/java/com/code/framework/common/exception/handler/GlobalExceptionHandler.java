package com.code.framework.common.exception.handler;

import com.code.framework.common.exception.BizException;
import com.code.framework.common.exception.enums.ExceptionCodeEnum;
import com.code.framework.common.result.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 愆凡
 * @date 2022/6/13 22:02
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 如果超过长度，前端交互体验不佳，使用默认错误消息
	 */
	static Integer MAX_LENGTH = 200;

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public CommonResult<Object> runtimeExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {

		log.error("[异常拦截 : RuntimeException]", e);

		return CommonResult.error(ExceptionCodeEnum.COMMON_ERROR);
	}

	@ExceptionHandler(BizException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public CommonResult<Object> handleServiceException(HttpServletRequest request, final Exception e, HttpServletResponse response) {
		// 若为自定义异常，则返回自定义错误消息
		if (e instanceof BizException bizException) {

			log.error("[异常拦截 : ServiceException] : {}-{}", bizException.getCode(), bizException.getMessage(), e);

			return CommonResult.error(bizException);
		} else {
			log.error("[异常拦截 : OtherException] : ", e);

			String errorMsg = "服务器异常，请稍后重试";
			if (e != null && e.getMessage() != null && e.getMessage().length() < MAX_LENGTH) {
				errorMsg = e.getMessage();
			}
			return CommonResult.error(ExceptionCodeEnum.COMMON_ERROR.code(), errorMsg);
		}
	}

}
