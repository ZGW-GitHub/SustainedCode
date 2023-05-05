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

package com.code.spring.bean.create;

import com.code.spring.bean.create.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Snow
 * @date 2022/8/10 16:38
 */
@Slf4j
public class WayConstructorApplication {
	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		// 加载配置文件中定义的 Bean
		// XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(context);
		// xmlReader.loadBeanDefinitions(new ClassPathResource("beans.xml"));

		// 扫描指定包下注解配置的 Bean
		context.scan("com.code.spring.bean.create");

		context.refresh();

		User bean = context.getBean(User.class);
		System.err.println(bean);

	}
}
