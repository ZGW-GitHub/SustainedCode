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

package com.code.spring.aop.auto.by.anno.aop.component.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 所有的切入点定义在这里
 *
 * @author Snow
 * @date 2021/7/23 10:08
 */
@Slf4j
@Component
public class AllPointcut {

	/**
	 * {@link Pointcut} 匹配哪些方法需要被拦截增强
	 */
	@Pointcut("@annotation(com.code.spring.aop.auto.by.anno.anno.CheckParams)")
	public void checkParams() {}

	/**
	 * {@link Pointcut} 匹配哪些方法需要被拦截增强
	 */
	@Pointcut("@annotation(com.code.spring.aop.auto.by.anno.anno.LogPrint)")
	public void logPrint() {}

}
