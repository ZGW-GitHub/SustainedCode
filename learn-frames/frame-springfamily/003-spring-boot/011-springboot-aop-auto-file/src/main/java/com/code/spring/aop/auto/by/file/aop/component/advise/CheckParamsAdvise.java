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

package com.code.spring.aop.auto.by.file.aop.component.advise;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * checkParams 用到的通知都定义在这里
 *
 * @author Snow
 * @date 2021/6/29 10:07
 */
@Slf4j
@Component
public class CheckParamsAdvise {

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
