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

package com.code.framework.basic.web.api;

import cn.hutool.core.util.StrUtil;
import com.code.framework.basic.web.api.annotation.Api;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @author Snow
 * @date 2023/5/21 14:33
 */
@Slf4j
@Getter
public class ApiDescriptor {

	/**
	 * api 参数个数
	 */
	public static final  int     API_PARAM_COUNT = 1;
	/**
	 * 方法签名拆分正则
	 */
	private static final Pattern PATTERN         = Pattern.compile("\\s+(.*)\\s+((.*)\\.(.*))\\((.*)\\)", Pattern.DOTALL);

	/**
	 * 对应 {@link Api} 中 code 取值
	 */
	private final String apiCode;
	/**
	 * 方法对象
	 */
	private final Method method;
	/**
	 * Spring Bean 名称
	 */
	private final String beanName;

	public ApiDescriptor(String apiCode, Method method, String beanName) {
		this.apiCode = apiCode;
		this.method = method;
		this.beanName = beanName;
	}

	private List<String> splitMethodName(Method method) {
		String methodGenericString = method.toGenericString();
		if (StrUtil.isBlank(methodGenericString)) {
			return Collections.emptyList();
		}

		final Matcher matcher = PATTERN.matcher(methodGenericString);
		if (matcher.find()) {
			return IntStream.rangeClosed(0, matcher.groupCount()).boxed().map(matcher::group).map(StrUtil::cleanBlank).toList();
		}
		return Collections.emptyList();
	}

}
