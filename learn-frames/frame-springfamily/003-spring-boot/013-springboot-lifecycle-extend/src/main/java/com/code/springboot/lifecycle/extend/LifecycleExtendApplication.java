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

package com.code.springboot.lifecycle.extend;

import com.code.springboot.lifecycle.extend.component.YamlPropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Snow
 */
@Slf4j
@SpringBootApplication
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class, name = "root", ignoreResourceNotFound = true, encoding = "UTF-8")
@PropertySource(value = "classpath:application-app.yml", factory = YamlPropertySourceFactory.class, name = "app", ignoreResourceNotFound = true, encoding = "UTF-8")
public class LifecycleExtendApplication {
	public static void main(String[] args) {

		new SpringApplicationBuilder(LifecycleExtendApplication.class).run(args);

	}
}
