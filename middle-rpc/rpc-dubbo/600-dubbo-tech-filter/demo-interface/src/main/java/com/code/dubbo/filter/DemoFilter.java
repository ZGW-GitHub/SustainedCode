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

package com.code.dubbo.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * group 指定了 Filter 在 provider 生效还是在 consumer 生效 ，或都生效
 *
 * @author Snow
 * @date 2021/5/22 16:45
 */
@Activate(group = {"provider", "consumer"}, order = -1000)
public class DemoFilter implements Filter, Filter.Listener {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		System.err.println("过滤器 - 目标方法调用前 ：" + invocation.getServiceName() + "#" + invocation.getMethodName());

		Result result = invoker.invoke(invocation);

		System.err.println("过滤器 - 目标方法调用后");

		return result;
	}

	@Override
	public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
		System.err.println("过滤器 - onResponse");
	}

	@Override
	public void onError(Throwable throwable, Invoker<?> invoker, Invocation invocation) {
		System.err.println("过滤器 - onError");
	}

}
