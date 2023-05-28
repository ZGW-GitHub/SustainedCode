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

package com.code.framework.web.api.invoker.method;

import cn.hutool.json.JSONUtil;
import com.code.framework.web.api.ApiDescriptor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Snow
 * @date 2023/5/23 20:22
 */
@Configuration(proxyBeanMethods = false)
public interface ApiMethodInvoker {

	Object invoke(ApiDescriptor apiDescriptor, Object springBean, String content) throws InvocationTargetException, IllegalAccessException;

	static ApiMethodInvoker getInvoker() {
		return (ApiDescriptor apiDescriptor, Object springBean, String content) -> {
			Class<?>[] parameterTypes = apiDescriptor.method().getParameterTypes();
			// 存在参数
			if (parameterTypes.length > 0) {
				Object paramObj = JSONUtil.toBean(content, parameterTypes[0]);
				return apiDescriptor.method().invoke(springBean, paramObj);
			}
			// 没有参数
			return apiDescriptor.method().invoke(springBean);
		};
	}

}
