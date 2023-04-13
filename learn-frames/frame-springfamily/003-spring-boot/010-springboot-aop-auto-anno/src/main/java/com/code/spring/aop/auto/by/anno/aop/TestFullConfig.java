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

package com.code.spring.aop.auto.by.anno.aop;

import com.code.spring.aop.auto.by.anno.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Snow
 * @date 2022/11/29 19:35
 */
@Slf4j
@Configuration(proxyBeanMethods = true)
public class TestFullConfig {

	@Bean("fullTest")
	public User test() {
		return new User().setId(1).setName("test");
	}

	@Bean("fullDemo")
	public User demo() {
		User test = test();
		test.setName("demo");
		return test;
	}

}
