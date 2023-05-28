package com.code.framework.basic.trace.context;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

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
		return CONTEXT_HOLDER.get();
	}

	public static Boolean hasTraceContext() {
		return CONTEXT_HOLDER.get() != null;
	}

	public static String getTraceId() {
		return Optional.ofNullable(getTraceContext()).map(TraceContext::getTraceId).orElse("");
	}

	public static void clear() {
		CONTEXT_HOLDER.remove();
	}

}
