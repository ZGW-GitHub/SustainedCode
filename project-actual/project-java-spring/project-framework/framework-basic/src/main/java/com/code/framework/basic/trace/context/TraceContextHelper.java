package com.code.framework.basic.trace.context;

import cn.hutool.json.JSONUtil;
import com.code.framework.basic.exception.code.BizExceptionCode;
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
		if (traceContext != null) {
			throw BizExceptionCode.TRACE_EXCEPTION.exception();
		}

		traceContext = new TraceContext();
		// TODO 接入 Security 后修改
		traceContext.addInfo(TraceContextKeyEnum.USER_INFO, JSONUtil.toJsonStr(new TraceUserInfo().setUserId("666").setUserName("test")));
		CONTEXT_HOLDER.set(traceContext);
		return traceContext;
	}

	public static void startTrace(String traceId) {
		TraceContext traceContext = startTrace();
		traceContext.addInfo(TraceContextKeyEnum.TRACE_ID, traceId);
	}

	public static TraceContext startTrace(TraceContext parentTraceContext) {
		TraceContext traceContext = CONTEXT_HOLDER.get();
		if (traceContext != null) {
			throw BizExceptionCode.TRACE_EXCEPTION.exception();
		}

		traceContext = new TraceContext(parentTraceContext);
		CONTEXT_HOLDER.set(traceContext);
		return traceContext;
	}

	public static void setTraceContext(TraceContext context) {
		CONTEXT_HOLDER.set(context);
	}

	public static TraceContext currentTraceContext() {
		return CONTEXT_HOLDER.get();
	}

	public static Boolean hasTraceContext() {
		return CONTEXT_HOLDER.get() != null;
	}

	public static void addInfo(TraceContextKeyEnum key, String value) {
		CONTEXT_HOLDER.get().addInfo(key, value);
	}

	public static String getInfo(TraceContextKeyEnum key) {
		return CONTEXT_HOLDER.get().getInfo(key);
	}

	public static String getTraceId() {
		return Optional.ofNullable(currentTraceContext()).map(TraceContext::getTraceId).orElse("");
	}

	public static void clear() {
		CONTEXT_HOLDER.remove();
	}

}
