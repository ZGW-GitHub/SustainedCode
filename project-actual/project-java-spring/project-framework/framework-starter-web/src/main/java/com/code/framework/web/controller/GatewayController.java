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

package com.code.framework.web.controller;

import cn.hutool.core.util.StrUtil;
import com.code.framework.basic.trace.IdGenerator;
import com.code.framework.basic.trace.context.TraceContext;
import com.code.framework.basic.trace.context.TraceContextHelper;
import com.code.framework.basic.trace.context.TraceContextKeyEnum;
import com.code.framework.web.api.invoker.ApiInvoker;
import com.code.framework.web.controller.domain.GatewayRequest;
import com.code.framework.web.controller.domain.GatewayResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Snow
 * @date 2023/5/20 20:00
 */
@Slf4j
@RestController
public class GatewayController {

	@Resource
	private ApiInvoker apiInvoker;

	@PostMapping("gateway")
	public GatewayResponse<?> gateway(@RequestBody GatewayRequest gatewayRequest) throws Throwable {
		log.debug("【 Gateway 请求 】\nrequest : {}", gatewayRequest);

		// 1、生成/获取 traceId
		String traceId = StrUtil.isBlank(gatewayRequest.getTraceId()) ? IdGenerator.generateTraceId() : gatewayRequest.getTraceId();

		// 2、将 traceId 设置到 ThreadLocal
		TraceContext traceContext = TraceContextHelper.startTrace();
		traceContext.addInfo(TraceContextKeyEnum.TRACE_ID, traceId);

		try {
			// 3、调用 API
			Object result = apiInvoker.invoke(gatewayRequest.getApi(), gatewayRequest.getVersion(), gatewayRequest.getContent());

			log.debug("【 Gateway 响应 】\nrequest : {}\nresponse : {}", gatewayRequest, result);

			// 5、返回 response
			return GatewayResponse.success(result);
		} catch (InvocationTargetException | IllegalAccessException e) {
			log.error("【 Gateway 异常 】——【 API Method Invoke 发生异常 】异常信息 : {}", e.getMessage(), e);
			throw e;
		} catch (Throwable t) {
			log.error("【 Gateway 异常 】——【 发生 Throwable 异常 】异常信息 : {}", t.getMessage(), t);
			throw t;
		} finally {
			// 4、清除 ThreadLocal
			TraceContextHelper.clear();
		}
	}

}
