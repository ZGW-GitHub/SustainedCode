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

import com.code.framework.web.api.ApiDescriptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Snow
 * @date 2023/5/23 18:08
 */
@Component
public class DefaultApiInvoker extends ApiInvoker {

	@Override
	protected Object doInvoke(ApiDescriptor apiDescriptor, Object springBean, String content, ApiMethodInvoker apiMethodInvoker) throws InvocationTargetException, IllegalAccessException {
		// 登录校验

		// 权限校验

		// ...

		// 调用 API‘s Method
		return apiMethodInvoker.invoke(apiDescriptor, springBean, content);
	}

}
