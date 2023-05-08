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

package com.code.spring.boot.component.aop;

import com.code.spring.boot.component.annotion.DynamicDataSource;
import com.code.spring.boot.component.datasource.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Snow
 * @date 2023/5/8 14:41
 */
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAop implements Ordered {

	@Pointcut("@annotation(com.code.spring.boot.component.annotion.DynamicDataSource)")
	public void dynamicDataSource() {
	}

	@Around("com.code.spring.boot.component.aop.DynamicDataSourceAop.dynamicDataSource()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		// 获取被 AOP 拦截的方法
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = null;
		if (!(signature instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		methodSignature = (MethodSignature) signature;
		Object target = joinPoint.getTarget();
		Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

		// 获取方法上的 DynamicDataSource 注解
		DynamicDataSource dynamicDataSourceAnno = currentMethod.getAnnotation(DynamicDataSource.class);

		// 如果有DataSource注解，则设置DataSourceContextHolder为注解上的名称
		if (dynamicDataSourceAnno != null) {
			DynamicDataSourceContextHolder.set(dynamicDataSourceAnno.value());
			log.debug("设置数据源为：" + dynamicDataSourceAnno.value());
		}

		try {
			return joinPoint.proceed();
		} finally {
			log.debug("清空数据源信息！");
			DynamicDataSourceContextHolder.clear();
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
