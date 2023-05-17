package com.code.framework.common.exception.enums;

import lombok.AllArgsConstructor;

/**
 * @author 愆凡
 * @date 2022/6/13 21:51
 */
@AllArgsConstructor
public enum ExceptionCodeEnum {

	/**
	 * 通用错误码
	 */
	COMMON_ERROR(400, "服务器繁忙，请稍后重试"),

	/**
	 * RuntimeException
	 */
	RUNTIME_EXCEPTION(401, "服务器繁忙，请稍后重试"),

	/**
	 * 参数异常
	 */
	PARAMS_ERROR(4001, "参数异常"),

	/**
	 * 系统异常
	 */
	LIMIT_ERROR(1001, "访问过于频繁，请稍后再试"),
	ILLEGAL_REQUEST(1002, "非法请求，请重新刷新页面操作"),

	/**
	 * 定时任务
	 */
	BAD_XXL_JOB_HANDLER(2001, "定时任务配置错误");

	private final Integer code;
	private final String  message;

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

}
