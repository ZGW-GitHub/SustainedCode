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

import com.code.framework.web.api.invoker.ApiInvoker;
import com.code.framework.web.controller.domain.GatewayRequest;
import com.code.framework.web.controller.domain.GatewayResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

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
		try {
			// 1、调用 API
			log.debug("【 Gateway 请求 】request : {}", gatewayRequest);
			Object result = apiInvoker.invoke(gatewayRequest.getApi(), gatewayRequest.getVersion(), gatewayRequest.getContent());
			log.debug("【 Gateway 响应 】request : {} , response : {}", gatewayRequest, result);

			// 2、返回 response
			return GatewayResponse.success(result);
		} catch (InvocationTargetException e) {
			if (Objects.nonNull(e.getCause())) {
				throw e.getCause();
			}
			throw e;
		}
	}

}
