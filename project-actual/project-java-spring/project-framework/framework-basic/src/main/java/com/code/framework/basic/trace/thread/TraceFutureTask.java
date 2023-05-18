package com.code.framework.basic.trace.thread;

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

	private final TraceContext mainThreadTraceContext;

	public TraceFutureTask(Callable<V> callable) {
		super(callable);
		mainThreadTraceContext = TraceContextHelper.getTraceContext();
	}

	public TraceFutureTask(Runnable runnable, V result) {
		super(runnable, result);
		mainThreadTraceContext = TraceContextHelper.getTraceContext();
	}

}
