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

package com.code.spring.aop.manually;

import com.code.spring.aop.manually.service.DemoService;
import com.code.spring.aop.manually.service.DemoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.*;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Snow
 * @date 2022/6/6 15:44
 */
@Slf4j
@SpringBootTest
class ManuallyAopApplicationTest {

	public static final String USER_NAME = "fuck";

	/**
	 * 使用 AdvisedSupport 创建
	 */
	@Test
	void test1() {
		// 1、用来配置代理所需组件
		AdvisedSupport advisedSupport = new AdvisedSupport();

		// 2、设置代理的目标对象
		advisedSupport.setTarget(new DemoServiceImpl());
		// 必须设置为 true ，否则使用 AopContext.currentProxy() 会报错
		advisedSupport.setExposeProxy(true);
		advisedSupport.setProxyTargetClass(true);

		// 3、添加通知
		advisedSupport.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
			System.err.printf("方法[%s]被调用\n", method.getName());
		});

		// 4、利用 AopProxyFactory 并根据 AdvisedSupport 创建 AopProxy（AopProxy 接口有两个实现类:JdkDynamicAopProxy、ObjenesisCglibAopProxy）
		AopProxyFactory aopProxyFactory = new DefaultAopProxyFactory();
		// 这里创建出的 AopProxy 是 JdkDynamicAopProxy 还是 ObjenesisCglibAopProxy 会根据 AdvisedSupport 中的信息来决定
		AopProxy proxy = aopProxyFactory.createAopProxy(advisedSupport);

		DemoService service = (DemoService) proxy.getProxy();

		System.err.println(service.login(USER_NAME));
	}

	/**
	 * 使用 ProxyFactory 创建
	 */
	@Test
	void test2() {
		// 1、创建代理工厂
		ProxyFactory proxyFactory = new ProxyFactory();

		// 2、设置代理的目标对象
		proxyFactory.setTarget(new DemoServiceImpl());
		// 必须设置为 true ，否则使用 AopContext.currentProxy() 会报错
		proxyFactory.setExposeProxy(true);
		proxyFactory.setProxyTargetClass(true);

		// 3、添加通知
		proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
			System.err.printf("方法[%s]被调用\n", method.getName());
		});

		// 4、创建代理
		DemoService service = (DemoService) proxyFactory.getProxy();

		System.err.println(service.login(USER_NAME));
	}
	
}
