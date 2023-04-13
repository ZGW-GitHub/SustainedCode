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

package com.code.spring.way_generic;

import com.code.spring.component.DemoConfig;
import com.code.spring.UseApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Snow
 * @date 2022/4/23 22:37
 */
@Slf4j
public class AnnotationConfigApplicationContextTest {
	public static void main(String[] args) {

		// 1、创建基于注解的 Spring 应用上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 2、调用 ClassPathBeanDefinitionScanner 扫描组件：构建 BeanDefinition，并存入 BeanFactory 的 beanDefinitionMap 中
		context.scan("com.code.spring.component");
		// 2、调用 AnnotatedBeanDefinitionReader 注册指定的类：构建 BeanDefinition，并存入 BeanFactory 的 beanDefinitionMap 中
		context.register(DemoConfig.class);
		// 3、刷新 Spring 应用上下文
		context.refresh();

		// 可选：发布 Spring 上下文启动完成事件
		context.start();

		// 使用
		UseApplicationContextUtil.useApplicationContext(context);

	}
}
