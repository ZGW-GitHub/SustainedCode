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

package com.code.spring.aop.auto.by.file.service;

import com.code.spring.aop.auto.by.file.anno.LogPrint;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * @author Snow
 * @date 2021/11/5 18:52
 */
@Slf4j
@Setter
@Service
public class DemoServiceImpl implements DemoService {

	@LogPrint
	@Override
	public String login(String loginName) {
		System.err.printf("用户[%s]登录系统\n", loginName);

		// 这里必须使用 AopContext.currentProxy() ，否则 findUser 方法不会被 AOP 拦截
		return ((DemoService) AopContext.currentProxy()).findUser(loginName);
	}

	@LogPrint
	@Override
	public String findUser(String loginName) {
		System.err.println("查询用户");

		return "查询到-" + loginName;
	}

}
