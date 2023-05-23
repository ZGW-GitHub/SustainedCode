package com.code.framework.basic.trace.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/13 16:49
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum TraceContextKeyEnum {

	/**
	 * 唯一标识
	 */
	UNIQUE_ID("UNIQUE_ID"),

	/**
	 * 链路 ID
	 */
	TRACE_ID("TRACE_ID"),

	/**
	 * 任务 ID
	 */
	JOB_ID("JOB_ID"),

	/**
	 * 异步任务 ID
	 */
	ASYNC_TASK_ID("ASYNC_TASK_ID"),
	;

	private final String name;

}
