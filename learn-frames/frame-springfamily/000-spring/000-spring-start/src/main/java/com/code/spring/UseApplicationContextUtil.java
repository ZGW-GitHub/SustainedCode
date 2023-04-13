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

package com.code.spring;

import com.code.spring.component.DemoConfig;
import com.code.spring.component.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Optional;

/**
 * @author Snow
 * @date 2022/6/30 11:11
 */
@Slf4j
public class UseApplicationContextUtil {

	public static void useApplicationContext(AbstractApplicationContext context) {
		DemoService demoService = context.getBean(DemoService.class);
		System.err.println(Optional.of(demoService).map(DemoService::toString).orElse(""));

		DemoConfig demoConfig = context.getBean(DemoConfig.class);
		System.err.println(Optional.of(demoConfig).map(DemoConfig::toString).orElse(""));
	}

}
