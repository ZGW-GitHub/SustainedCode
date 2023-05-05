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

package com.code.spring.custom.config.label;

import com.code.spring.custom.config.label.schema.AppConfig;
import com.code.spring.custom.config.label.schema.ConsumerConfig;
import com.code.spring.custom.config.label.schema.ProviderConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Snow
 * @date 2022/4/19 23:09
 */
@Slf4j
@SpringBootApplication
@ImportResource("classpath:config.xml")
public class CustomConfigLabelApplication {
	public static void main(String[] args) {

		ConfigurableApplicationContext context = new SpringApplicationBuilder(CustomConfigLabelApplication.class).run(args);

		System.err.println(context.getBean(AppConfig.class));
		System.err.println(context.getBean(ProviderConfig.class));
		System.err.println(context.getBean(ConsumerConfig.class));

	}
}
