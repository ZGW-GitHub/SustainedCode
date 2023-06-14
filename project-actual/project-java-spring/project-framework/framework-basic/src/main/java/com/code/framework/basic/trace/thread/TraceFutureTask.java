package com.code.framework.basic.trace.thread;

import com.code.framework.basic.exception.code.BizExceptionCode;
import com.code.framework.basic.trace.context.TraceContext;
import com.code.framework.basic.trace.context.TraceContextHelper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author 愆凡
 * @date 2022/6/14 10:09
 */
@Slf4j
@Getter
public class TraceFutureTask<V> extends FutureTask<V> {

	/**
	 * 提交任务到线程池的线程的 TraceContext
	 */
	private final TraceContext traceContext;

	public TraceFutureTask(Callable<V> callable) {
		super(callable);
		traceContext = TraceContextHelper.currentTraceContext();

		if (traceContext == null) {
			throw BizExceptionCode.TRACE_CONTEXT_NOT_EXIST_EXCEPTION.exception();
		}
	}

	public TraceFutureTask(Runnable runnable, V result) {
		super(runnable, result);
		traceContext = TraceContextHelper.currentTraceContext();

		if (traceContext == null) {
			throw BizExceptionCode.TRACE_CONTEXT_NOT_EXIST_EXCEPTION.exception();
		}
	}

}
