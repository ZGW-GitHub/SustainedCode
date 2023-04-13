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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 工具类,用来创建代理对象
 *
 * @author Snow
 * @date 2021/9/14 21:26
 */
public class ProxyUtil {

	public static Object getProxy(Class<?> clazz, InvocationHandler handler) {
		return Proxy.newProxyInstance(clazz.getClassLoader(), // ClassLoader 对象 
				clazz.getInterfaces(), // 目标对象实现的接口
				handler); // 被代理对象的调用处理器,增强逻辑就定义在里面
	}
}
