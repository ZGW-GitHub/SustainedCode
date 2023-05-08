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

package com.code.spring.boot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <li>{@link @Aspect} ：标识该类是一个定义通知的配置类，以使该类中定义的通知生效</li>
 *
 * @author Snow
 * @date 2023/5/8 17:06
 */
@Aspect
@Component
public class CheckParamsAop {

	@Pointcut("@annotation(com.code.spring.boot.aop.anno.CheckParams)")
	public void checkParams() {
	}

	@Before("checkParams()")
	public void before(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();

		for (Object arg : args) {
			if (arg == null) {
				System.err.println("入参非法");
				throw new RuntimeException("入参非法");
			}
		}
		System.err.println("入参正常");
	}

}
