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

package com.code.spring.custom.scope;

import com.code.spring.custom.scope.component.RefreshScope;
import com.code.spring.custom.scope.component.ThreadScope;
import com.code.spring.custom.scope.service.RefreshService;
import com.code.spring.custom.scope.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Snow
 * @date 2021/12/31 3:26 PM
 */
@Slf4j
@SpringBootApplication
public class CustomScopeApplication {
	public static void main(String[] args) throws InterruptedException {

		ConfigurableApplicationContext context = new SpringApplicationBuilder(CustomScopeApplication.class).run(args);

		// 向容器注册自定义的Scope
		context.getBeanFactory().registerScope(ThreadScope.SCOPE_THREAD, new ThreadScope());
		context.getBeanFactory().registerScope(RefreshScope.SCOPE_REFRESH, RefreshScope.getInstance());

		// 自定义 Scope ，ThreadScope 测试：
		// threadScopeTest(context);

		// Scope 的 proxyMode 属性，原理探究：
		proxyModeTest(context);

	}

	private static void threadScopeTest(ConfigurableApplicationContext context) throws InterruptedException {
		// 启动几个线程，从容器中获取Bean
		for (int i = 1; i <= 2; i++) {
			new Thread(() -> {
				System.err.println("**********************************");
				Object one = context.getBean(ThreadService.class);
				System.err.println(Thread.currentThread() + " 第一次获取 ： " + one);
				Object two = context.getBean(ThreadService.class);
				System.err.println(Thread.currentThread() + " 第二次获取 ： " + two);
				System.err.println("**********************************");
			}).start();

			TimeUnit.SECONDS.sleep(1);
		}
	}

	private static void proxyModeTest(ConfigurableApplicationContext context) {
		RefreshService bean = context.getBean(RefreshService.class);
		System.err.println("获取 Bean ：" + bean);
		System.err.println("Bean 的 Class ：" + bean.getClass());

		System.err.println();
		IntStream.rangeClosed(1, 2).forEach(j -> {
			System.err.println("**********************************");
			System.err.println("第 " + j + " 次调用 getServiceId ：" + bean.getServiceId());
			System.err.println("第 " + j + " 次调用 test ：" + bean.test());
			System.err.println("**********************************\n");
		});
	}

}
