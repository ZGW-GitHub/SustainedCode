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

package com.code.framework.web.api.invoker;

import cn.hutool.core.util.StrUtil;
import com.code.framework.web.api.ApiContainer;
import com.code.framework.web.api.ApiDescriptor;
import com.code.framework.web.api.exception.ApiExceptionCode;
import com.code.framework.web.api.invoker.method.ApiMethodInvoker;
import com.code.framework.web.component.ApplicationContextHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author Snow
 * @date 2023/5/23 16:20
 */
@Slf4j
@Component
public abstract class ApiInvoker {

	@Resource
	private ApiContainer apiContainer;

	public Object invoke(String api, String version, String content) throws InvocationTargetException, IllegalAccessException {
		if (StrUtil.isBlank(api)) {
			throw ApiExceptionCode.API_INVOKE_EXCEPTION_API_NOT_EXIST.exception();
		}

		String apiIdentification = api + "_" + (StrUtil.isBlank(version) ? ApiDescriptor.DEFAULT_VERSION : version);

		ApiDescriptor apiDescriptor = apiContainer.get(apiIdentification);
		if (Objects.isNull(apiDescriptor)) {
			throw ApiExceptionCode.API_INVOKE_EXCEPTION_API_NOT_EXIST.exception();
		}

		Object springBean = ApplicationContextHelper.getBean(apiDescriptor.beanName());
		if (Objects.isNull(springBean)) {
			throw ApiExceptionCode.API_INVOKE_EXCEPTION_API_NOT_EXIST.exception();
		}

		return doInvoke(apiDescriptor, springBean, content, ApiMethodInvoker.getInvoker());
	}

	protected abstract Object doInvoke(ApiDescriptor apiDescriptor, Object springBean, String content, ApiMethodInvoker apiMethodInvoker) throws InvocationTargetException, IllegalAccessException;

}
