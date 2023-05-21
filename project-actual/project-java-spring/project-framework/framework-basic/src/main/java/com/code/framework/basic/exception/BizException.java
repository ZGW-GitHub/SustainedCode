package com.code.framework.basic.exception;

import com.code.framework.basic.result.code.ExceptionResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/13 21:48
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

	/**
	 * 业务错误码
	 *
	 * @see ExceptionResultCode#getCode()
	 */
	private Integer code;

	/**
	 * 错误提示
	 *
	 * @see ExceptionResultCode#getMessage()
	 */
	private String message;

	public BizException(ExceptionResultCode resultCode) {
		this.code = resultCode.getCode();
		this.message = resultCode.getMessage();
	}

	public BizException(ExceptionResultCode resultCode, String msgFormat, Object... args) {
		this.code = resultCode.getCode();
		this.message = String.format(msgFormat, args);
	}

}
