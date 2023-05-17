package com.code.framework.common.trace.context;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
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
		parentTraceContext.getContext().forEach(this::addInfo);
	}

	public void addInfo(TraceContextKeyEnum key, String value) {
		if (value == null) {
			value = "";
		}
		contextInfo.put(key, value);
	}

	public String getInfo(TraceContextKeyEnum key) {
		return contextInfo.get(key);
	}

	public void removeInfo(TraceContextKeyEnum key) {
		contextInfo.remove(key);
	}

	public Map<TraceContextKeyEnum, String> getContext() {
		return contextInfo;
	}

}
