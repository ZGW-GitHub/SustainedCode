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

package com.code.nacos;

import com.code.nacos.entity.DynamicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Snow
 * @date 2022/4/17 21:36
 */
@Slf4j
@SpringBootApplication
public class NacosApplication {
	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(NacosApplication.class, args);

		DynamicConfig config = context.getBean(DynamicConfig.class);
		System.err.println(config.getName());

	}
}
