package com.code.framework.common.exception;

import com.code.framework.common.exception.enums.ExceptionCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/13 21:48
 */
@Slf4j
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

	/**
	 * 业务错误码
	 *
	 * @see ExceptionCodeEnum#code()
	 */
	private Integer code;

	/**
	 * 错误提示
	 *
	 * @see ExceptionCodeEnum#message()
	 */
	private String message;

	public BizException(ExceptionCodeEnum exceptionCode) {
		this.code = exceptionCode.code();
		this.message = exceptionCode.message();
	}

	public BizException(ExceptionCodeEnum exceptionCode, String message) {
		this.code = exceptionCode.code();
		this.message = message;
	}

}
