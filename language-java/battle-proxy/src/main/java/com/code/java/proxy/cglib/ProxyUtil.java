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

package com.code.java.proxy.cglib;

import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * 工具类,用来创建代理对象
 *
 * @author Snow
 * @date 2021/9/14 21:43
 */
public class ProxyUtil {

	public static Object getProxy(Class<?> clazz, Callback callback) {
		Enhancer enhancer = new Enhancer();

		// 代理类要继承的父类
		enhancer.setSuperclass(clazz);

		// 代理类要实现的接口
		enhancer.setInterfaces(clazz.getInterfaces());

		// 设置 Callback
		enhancer.setCallback(callback);
		
		// 设置代理类的类名生成规则
		enhancer.setNamingPolicy(DefaultNamingPolicy.INSTANCE); // DefaultNamingPolicy : 被代理类的类名 + "$$" + 处理该过程的类的类名 + "ByCGLIB" + "$$" + key的hashcode

		// 创建代理对象
		return enhancer.create();
	}

}
