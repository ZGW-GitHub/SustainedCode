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

package com.code.framework.dubbo.component.filter;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * group 指定了 Filter 在 provider 生效还是在 consumer 生效 ，或都生效
 *
 * @author Snow
 * @date 2021/5/22 16:45
 */
@Slf4j
@Activate(group = {"provider", "consumer"}, order = -1000)
public class LogFilter implements Filter, Filter.Listener {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		log.debug("【 Dubbo LogFilter 】- 目标方法执行前 : {}", invocation.getServiceName() + "#" + invocation.getMethodName());

		Result result = invoker.invoke(invocation);

		log.debug("【 Dubbo LogFilter 】- 目标方法执行后 : {}", invocation.getServiceName() + "#" + invocation.getMethodName());

		return result;
	}

	@Override
	public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
		log.debug("【 Dubbo LogFilter 】- 目标方法执行成功 : {}", invocation.getServiceName() + "#" + invocation.getMethodName());
	}

	@Override
	public void onError(Throwable throwable, Invoker<?> invoker, Invocation invocation) {
		log.debug("【 Dubbo LogFilter 】- 目标方法执行异常 : {}", ExceptionUtil.getRootCauseMessage(throwable));
	}

}
