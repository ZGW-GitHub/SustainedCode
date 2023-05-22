package com.code.framework.basic.result;

import com.code.framework.basic.exception.BizException;
import com.code.framework.basic.result.code.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author 愆凡
 * @date 2022/6/13 22:07
 */
@Slf4j
@Getter
public class CommonResult<T> implements Serializable {

	public static final int    SUCCESS_CODE = 200;
	public static final String SUCCESS_MSG  = "成功";

	/**
	 * 返回码
	 */
	private Integer code;

	/**
	 * 返回数据
	 */
	private T data;

	/**
	 * 错误提示
	 */
	private String message;

	private CommonResult() {
	}

	static CommonResult<String> success() {
		return success("");
	}

	static <T> CommonResult<T> success(T data) {
		CommonResult<T> result = new CommonResult<>();
		result.code = SUCCESS_CODE;
		result.message = SUCCESS_MSG;
		result.data = data;
		return result;
	}

	static <T> CommonResult<T> error(ExceptionCode exceptionCode) {
		return error(exceptionCode, exceptionCode.getMessage());
	}

	static <T> CommonResult<T> error(ExceptionCode exceptionCode, String message) {
		CommonResult<T> result = new CommonResult<>();
		result.code = exceptionCode.getCode();
		result.message = message;
		return result;
	}

	static <T> CommonResult<T> error(BizException bizException) {
		return error(bizException, bizException.getMessage());
	}

	static <T> CommonResult<T> error(BizException bizException, String message) {
		CommonResult<T> result = new CommonResult<>();
		result.code = bizException.getCode();
		result.message = message;
		return result;
	}

}
