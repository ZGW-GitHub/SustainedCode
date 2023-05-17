package com.code.framework.common.result.enums;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/13 22:12
 */
@Slf4j
@AllArgsConstructor
public enum ResultCodeEnum {

	/**
	 * 处理成功
	 */
	SUCCESS(200, "成功"),
	;

	/**
	 * 返回码
	 */
	private final Integer code;

	/**
	 * 提示信息
	 */
	private final String message;

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

}
