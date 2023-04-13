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

package com.code.spring.aop.auto.by.anno;

import com.code.spring.aop.auto.by.anno.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * @author Snow
 * @date 2022/5/30 20:32
 */
@Slf4j
@SpringBootApplication
public class AutoAopByAnnoApplication {
	public static void main(String[] args) {

		ConfigurableApplicationContext context = new SpringApplication(AutoAopByAnnoApplication.class).run(args);

		Map<String, User> beans = context.getBeansOfType(User.class);
		System.err.println(beans);

	}
}
