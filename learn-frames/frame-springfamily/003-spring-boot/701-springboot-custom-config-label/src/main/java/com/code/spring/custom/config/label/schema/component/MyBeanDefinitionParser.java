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

package com.code.spring.custom.config.label.schema.component;

import com.code.spring.custom.config.label.schema.AppConfig;
import com.code.spring.custom.config.label.schema.ConsumerConfig;
import com.code.spring.custom.config.label.schema.ProviderConfig;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.NonNull;
import org.w3c.dom.Element;

/**
 * @author Snow
 */
public class MyBeanDefinitionParser implements BeanDefinitionParser {

	private final Class<?> beanClass;

	public MyBeanDefinitionParser(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public BeanDefinition parse(@NonNull Element element, @NonNull ParserContext parserContext) {
		return parse(element, parserContext, beanClass);
	}

	private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass) {
		if (AppConfig.class.equals(beanClass)) {
			return parseAppConfig(element, parserContext, beanClass);
		} else if (ProviderConfig.class.equals(beanClass)) {
			return parseProviderConfig(element, parserContext, beanClass);
		} else if (ConsumerConfig.class.equals(beanClass)) {
			return parseConsumerConfig(element, parserContext, beanClass);
		}

		throw new RuntimeException("不支持的解析");
	}

	private RootBeanDefinition parseAppConfig(Element element, ParserContext parserContext, Class<?> beanClass) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();

		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setLazyInit(false);
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("name", element.getAttribute("name"))
					  .add("address", element.getAttribute("address"))
					  .add("port", element.getAttribute("port"));

		beanDefinition.setPropertyValues(propertyValues);

		parserContext.getRegistry().registerBeanDefinition("appConfig", beanDefinition);

		return beanDefinition;
	}

	private RootBeanDefinition parseProviderConfig(Element element, ParserContext parserContext, Class<?> beanClass) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();

		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setLazyInit(false);
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("serviceId", element.getAttribute("serviceId"))
					  .add("protocol", element.getAttribute("protocol"));

		beanDefinition.setPropertyValues(propertyValues);

		parserContext.getRegistry().registerBeanDefinition("providerConfig", beanDefinition);

		return beanDefinition;
	}

	private static RootBeanDefinition parseConsumerConfig(Element element, ParserContext parserContext, Class<?> beanClass) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();

		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setLazyInit(false);
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("serviceId", element.getAttribute("serviceId"))
					  .add("protocol", element.getAttribute("protocol"))
					  .add("checkProvider", element.getAttribute("checkProvider"));

		beanDefinition.setPropertyValues(propertyValues);

		parserContext.getRegistry().registerBeanDefinition("consumerConfig", beanDefinition);

		return beanDefinition;
	}

}
