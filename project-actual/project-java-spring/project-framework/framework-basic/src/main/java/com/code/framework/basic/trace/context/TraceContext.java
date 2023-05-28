package com.code.framework.basic.trace.context;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 愆凡
 * @date 2022/6/13 16:47
 */
@Slf4j
public class TraceContext {

	private final Map<TraceContextKeyEnum, String> contextInfo = new ConcurrentHashMap<>();

	public TraceContext() {
		addInfo(TraceContextKeyEnum.UNIQUE_ID, IdUtil.fastSimpleUUID());
	}

	public TraceContext(TraceContext parentTraceContext) {
		parentTraceContext.getInfos().forEach(this::addInfo);
	}

	public void addInfo(TraceContextKeyEnum key, String value) {
		if (TraceContextKeyEnum.TRACE_ID.equals(key) && contextInfo.containsKey(TraceContextKeyEnum.TRACE_ID)) {
			log.warn("【 链路追踪 】>>>>>> 线程【 {} 】试图覆盖 TRACE_ID ，线程栈：{}", Thread.currentThread().getName(), Thread.currentThread().getStackTrace());
			return;
		}

		value = Optional.ofNullable(value).orElse(StrUtil.EMPTY);
		contextInfo.put(key, value);
	}

	public void removeInfo(TraceContextKeyEnum key) {
		contextInfo.remove(key);
	}

	public String getInfo(TraceContextKeyEnum key) {
		return contextInfo.get(key);
	}

	public String getTraceId() {
		return contextInfo.getOrDefault(TraceContextKeyEnum.TRACE_ID, "");
	}

	public Map<TraceContextKeyEnum, String> getInfos() {
		return contextInfo;
	}

}
