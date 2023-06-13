/*
 * Copyright (C) <2023> <Snow>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.code.framework.basic.util;

import com.code.framework.basic.trace.context.TraceContextKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @author Snow
 * @date 2023/6/9 20:29
 */
@Slf4j
public class MDCUtil {

	public static void setTraceId(String traceId) {
		MDC.put(TraceContextKeyEnum.TRACE_ID.getName(), traceId);
	}

	public static void removeTraceId() {
		MDC.remove(TraceContextKeyEnum.TRACE_ID.getName());
	}

	public static void clear() {
		MDC.clear();
	}

}
