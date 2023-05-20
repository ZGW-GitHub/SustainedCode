package com.code.framework.basic.exception;

import com.code.framework.basic.result.code.ResultCode;
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
	 * @see ResultCode#getCode()
	 */
	private Integer code;

	/**
	 * 错误提示
	 *
	 * @see ResultCode#getMessage()
	 */
	private String message;

	public BizException(ResultCode resultCode) {
		this.code = resultCode.getCode();
		this.message = resultCode.getMessage();
	}

	public BizException(ResultCode resultCode, String message) {
		this.code = resultCode.getCode();
		this.message = message;
	}

}
