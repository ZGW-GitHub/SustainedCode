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

package com.code.java.proxy.jdk;

import com.code.java.proxy.jdk.invocation.handler.CustomServiceInvocationHandler;
import com.code.java.proxy.jdk.invocation.handler.DemoService;
import com.code.java.proxy.jdk.invocation.handler.DemoServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 */
public class SimpleTest {

	/**
	 * 入门示例
	 */
	@Test
	void simpleTest() {
		// 创建被代理对象
		DemoServiceImpl demoService = new DemoServiceImpl();

		// 创建被代理对象的方法调用处理器
		CustomServiceInvocationHandler handler = new CustomServiceInvocationHandler(demoService);

		// 创建代理对象
		DemoService proxy = (DemoService) ProxyUtil.getProxy(DemoServiceImpl.class, handler);

		// 调用方法
		// 该调用会被转发到 handler 的 invoker 方法
		proxy.addUser();
	}

}
