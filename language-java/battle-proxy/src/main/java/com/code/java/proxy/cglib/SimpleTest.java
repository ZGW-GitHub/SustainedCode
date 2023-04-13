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

import com.code.java.proxy.cglib.dispatcher.BlogTypeModel;
import com.code.java.proxy.cglib.lazy.loader.BlogModel;
import com.code.java.proxy.cglib.method.interceptor.CustomMethodInterceptor;
import com.code.java.proxy.cglib.method.interceptor.DemoService;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 */
public class SimpleTest {

	/**
	 * MethodInterceptor 示例
	 */
	@Test
	void methodInterceptorTest() {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/snow/Downloads");

		CustomMethodInterceptor interceptor = new CustomMethodInterceptor();

		DemoService proxy = (DemoService) ProxyUtil.getProxy(DemoService.class, interceptor);

		proxy.addUser();
	}

	/**
	 * LazyLoader 示例
	 * 背景:博客内容太大,只在需要时才会去从数据库查询
	 */
	@Test
	void lazyLoaderTest() {
		BlogModel blog = new BlogModel("CGLIB");

		System.err.printf("打印 3 次博客[%s]的内容:\n", blog.getTitle());
		System.err.println(blog.getBlogContentModel().toString());
		System.err.println(blog.getBlogContentModel().toString());
		System.err.println(blog.getBlogContentModel().toString());
	}

	/**
	 * LazyLoader 示例
	 * 背景:各类型博客的数量,每次获取时都要去从数据库查询最新的
	 */
	@Test
	void dispatcherTest() {
		BlogTypeModel blog = new BlogTypeModel("Java");

		System.err.printf("打印 3 次[%s]分类下博客的数量:\n", blog.getName());
		System.err.println(blog.getBlogCountModel().toString());
		System.err.println(blog.getBlogCountModel().toString());
		System.err.println(blog.getBlogCountModel().toString());
	}

}
