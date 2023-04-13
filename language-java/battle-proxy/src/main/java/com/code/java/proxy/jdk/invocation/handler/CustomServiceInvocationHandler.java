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

package com.code.java.proxy.jdk.invocation.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 被代理对象的调用处理器,会拦截代理对象的方法调用
 *
 * @author Snow
 * @date 2021/9/14 21:08
 */
public class CustomServiceInvocationHandler implements InvocationHandler {

	/**
	 * 被代理的对象
	 */
	private final Object target;

	/**
	 * 构造函数
	 *
	 * @param target 被代理的对象
	 */
	public CustomServiceInvocationHandler(Object target) {
		this.target = target;
	}

	/**
	 * 在该方法里编写被代理方法的增强逻辑
	 *
	 * @param proxy  代理对象
	 * @param method 调用的方法
	 * @param args   调用的方法的入参
	 *
	 * @return 调用的方法的返回值
	 *
	 * @throws Throwable e
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.err.printf("执行目标方法 %s 前的增强\n", method.getName());

		// 调用被代理对象的方法
		Object result = method.invoke(target, args);

		System.err.printf("执行目标方法 %s 后的增强\n", method.getName());

		return result;
	}

}
