package com.code.framework.common.result;

import cn.hutool.core.lang.Assert;
import com.code.framework.common.exception.BizException;
import com.code.framework.common.exception.enums.ExceptionCodeEnum;
import com.code.framework.common.result.enums.ResultCodeEnum;
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

	public static <T> CommonResult<T> success(T data) {
		CommonResult<T> result = new CommonResult<>();
		result.code = ResultCodeEnum.SUCCESS.code();
		result.message = ResultCodeEnum.SUCCESS.message();
		result.data = data;
		return result;
	}

	public static <T> CommonResult<T> error(ResultCodeEnum resultCode) {
		Assert.isTrue(!ResultCodeEnum.SUCCESS.equals(resultCode), "返回码不匹配");

		return error(resultCode.code(), resultCode.message());
	}

	public static <T> CommonResult<T> error(BizException bizException) {
		return error(bizException.getCode(), bizException.getMessage());
	}

	public static <T> CommonResult<T> error(ExceptionCodeEnum exceptionCode) {
		return error(exceptionCode.code(), exceptionCode.message());
	}

	public static <T> CommonResult<T> error(Integer code, String message) {
		CommonResult<T> result = new CommonResult<>();
		result.code = code;
		result.message = message;
		return result;
	}

}
