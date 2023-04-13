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

import com.code.spring.UseApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Snow
 * @date 2022/4/24 21:48
 */
@Slf4j
public class GenericXmlApplicationContextTest {
	public static void main(String[] args) {

		// 1、创建 ApplicationContext
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		// 2、获取 GenericXmlApplicationContext 内的 xml BeanDefinitionReader
		XmlBeanDefinitionReader xmlReader = context.getReader();
		xmlReader.loadBeanDefinitions(new ClassPathResource("beans.xml")); // 解析并组装 BeanDefinition
		// 2、创建 properties BeanDefinitionReader
		PropertiesBeanDefinitionReader propertiesReader = new PropertiesBeanDefinitionReader(context);
		propertiesReader.loadBeanDefinitions(new ClassPathResource("beans.properties")); // 解析并组装 BeanDefinition
		// 3、refresh
		context.refresh();

		// 可选：发布 Spring 上下文启动完成事件
		context.start();

		// 使用
		UseApplicationContextUtil.useApplicationContext(context);

	}
}
