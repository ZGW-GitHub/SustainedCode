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
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Snow
 * @date 2021/10/29 00:04
 */
@Slf4j
@Component
public class DemoBeanPostProcessor implements BeanPostProcessor {

	/**
	 * 初始化前会被调用
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (BeanLifecycleApplication.BEAN_NAME.contains(beanName)) {
			System.err.println("BeanPostProcessor 的 postProcessBeforeInitialization 方法被调用");
		}

		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	/**
	 * 初始化后会被调用
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (BeanLifecycleApplication.BEAN_NAME.contains(beanName)) {
			System.err.println("BeanPostProcessor 的 postProcessAfterInitialization 方法被调用");
		}

		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

}
