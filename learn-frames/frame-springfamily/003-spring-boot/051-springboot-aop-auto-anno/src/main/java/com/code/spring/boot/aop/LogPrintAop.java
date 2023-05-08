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
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * <li>{@link @Aspect} ：标识该类是一个定义通知的配置类，以使该类中定义的通知生效</li>
 *
 * @author Snow
 * @date 2023/5/8 17:11
 */
@Aspect
@Component
public class LogPrintAop {

	@Pointcut("@annotation(com.code.spring.boot.aop.anno.LogPrint)")
	public void logPrint() {
	}

	@Before("logPrint()")
	public void before(JoinPoint joinPoint) {
		System.err.printf("【 方法执行前 】入参：%s\n", Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(returning = "result", pointcut = "logPrint()")
	public void afterReturning(Object result) {
		System.err.printf("【 方法执行成功 】返回值：%s\n", result);
	}

	/**
	 * 注意和 AfterReturning 的区别, after 会拦截正常返回和异常的情况
	 */
	@After("logPrint()")
	public void after() {
		System.err.println("【 方法执行后 】");
	}

	/**
	 * 对异常返回进行处理
	 */
	@AfterThrowing(throwing = "throwable", pointcut = "logPrint()")
	public void afterThrowing(Throwable throwable) {
		System.err.printf("【 方法执行抛出异常 】异常：%s\n", throwable.getMessage());
	}

	// @Around("logPrint()")
	// public Object around(ProceedingJoinPoint joinPoint) {
	// 	// 目标方法执行前
	// 	System.err.printf("【 方法执行前 around 】入参：%s\n", Arrays.toString(joinPoint.getArgs()));
	//
	// 	Object result = null;
	// 	try {
	// 		result = joinPoint.proceed(); // 调用目标方法
	//
	// 		System.err.printf("【 方法执行成功 around 】返回值：%s\n", result);
	// 	} catch (Throwable throwable) {
	// 		System.err.printf("【 方法执行抛出异常 around 】异常：%s\n", throwable.getMessage());
	//
	// 		throw new RuntimeException(throwable.getMessage());
	// 	} finally {
	// 		// 目标方法执行后
	// 		System.err.println("【 方法执行后 around 】");
	// 	}
	//
	// 	return result;
	// }

}
