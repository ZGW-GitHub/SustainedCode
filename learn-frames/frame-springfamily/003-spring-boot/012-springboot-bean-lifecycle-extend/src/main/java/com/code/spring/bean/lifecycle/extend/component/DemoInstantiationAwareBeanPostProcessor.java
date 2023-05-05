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

package com.code.spring.bean.lifecycle.extend.component;

import com.code.spring.bean.lifecycle.extend.BeanLifecycleApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Snow
 * @date 2021/10/28 23:59
 */
@Slf4j
@Component
public class DemoInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	/**
	 * 实例化前会被调用
	 *
	 * @return {@code not null} : 返回值将作为创建的 Bean对象 返回,不再继续执行后面的 Bean 构建流程<br/>
	 * {@code null} : 继续执行 Bean 的构建流程
	 */
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		if (BeanLifecycleApplication.BEAN_NAME.contains(beanName)) {
			System.err.println("BeanPostProcessor - InstantiationAwareBeanPostProcessor 的 postProcessBeforeInstantiation 方法被调用");
		}

		return null;
	}

	/**
	 * 实例化后会被调用
	 */
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		if (BeanLifecycleApplication.BEAN_NAME.contains(beanName)) {
			System.err.println("BeanPostProcessor - InstantiationAwareBeanPostProcessor 的 postProcessAfterInstantiation 方法被调用");
		}

		return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
	}

	/**
	 * 属性填充前会被调用
	 */
	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
		if (BeanLifecycleApplication.BEAN_NAME.contains(beanName)) {
			System.err.println("BeanPostProcessor - InstantiationAwareBeanPostProcessor 的 postProcessProperties 方法被调用");

			final MutablePropertyValues propertyValues;
			if (pvs instanceof MutablePropertyValues) {
				propertyValues = (MutablePropertyValues) pvs;
			} else {
				propertyValues = new MutablePropertyValues();
			}

			// 配置 id = 9
			propertyValues.add("id", "9");

			// 修改配置文件中的配置
			PropertyValue namePropertyValue = propertyValues.getPropertyValue("name");
			if (namePropertyValue != null) {
				// 因为 PropertyValue 的 value 属性为 final ，不能修改，所以这里只能先删除再添加
				propertyValues.removePropertyValue("name");
				propertyValues.add("name", "SnowV3");
			}

			return propertyValues;
		}

		return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
	}
}
