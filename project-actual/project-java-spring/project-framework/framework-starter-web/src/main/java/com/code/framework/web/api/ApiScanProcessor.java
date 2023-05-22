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

package com.code.framework.web.api;

import com.code.framework.web.api.annotation.Api;
import com.code.framework.web.api.exception.ApiException;
import com.code.framework.web.api.exception.ApiExceptionCode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Snow
 * @date 2023/5/21 14:56
 */
@Slf4j
@Component
public class ApiScanProcessor implements BeanPostProcessor {

	@Resource
	private ApiContainer apiContainer;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());

		for (Method method : methods) {
			Api apiAnno = AnnotationUtils.findAnnotation(method, Api.class);
			if (Objects.isNull(apiAnno)) {
				return bean;
			}

			int parameterCount = method.getParameterCount();
			if (ApiDescriptor.API_PARAM_COUNT != parameterCount) {
				log.error("【 API 加载 】发生异常：方法[ {} ], 参数个数不等于 1", method.toGenericString());
				throw ApiExceptionCode.API_PARAM_VALIDATE_EXCEPTION.newException(ApiException::new);
			}

			String api = apiAnno.value();
			ApiDescriptor apiDescriptor = new ApiDescriptor(api, method, beanName);
			apiContainer.put(api, apiDescriptor);
			log.info("【 API 加载 】API[ {} ]，加载成功，apiDescriptor : {}", api, apiDescriptor);
		}
		return bean;
	}
}
