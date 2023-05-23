package com.code.framework.basic.trace.context;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/13 16:47
 */
@Slf4j
public class TraceContextHelper {

	private static final ThreadLocal<TraceContext> CONTEXT_HOLDER = new ThreadLocal<>();

	public static TraceContext startTrace() {
		TraceContext traceContext = CONTEXT_HOLDER.get();
		if (traceContext == null) {
			traceContext = new TraceContext();
		} else {
			traceContext = new TraceContext(traceContext);
		}

		CONTEXT_HOLDER.set(traceContext);
		return traceContext;
	}

	public static void setTraceContext(TraceContext context) {
		CONTEXT_HOLDER.set(context);
	}

	public static TraceContext getTraceContext() {
		TraceContext traceContext = CONTEXT_HOLDER.get();
		if (traceContext == null) {
			traceContext = new TraceContext();
			CONTEXT_HOLDER.set(traceContext);
		}
		return traceContext;
	}

	public static String getTraceId() {
		return getTraceContext().getInfo(TraceContextKeyEnum.TRACE_ID);
	}

	public static void clear() {
		CONTEXT_HOLDER.remove();
	}

}
