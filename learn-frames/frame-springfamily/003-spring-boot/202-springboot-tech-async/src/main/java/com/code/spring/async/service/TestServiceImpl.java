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

package com.code.spring.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Snow
 * @date 2022/5/5 21:33
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

	@Override
	// @Async("threadPoolOne")
	public void test1() {
		log.info("test1 run ...");

		Object proxy = AopContext.currentProxy();

		((TestService) proxy).test2();
	}

	@Override
	@Async("threadPoolOne")
	public void test2() {
		log.info("test2 run ...");
	}

}
