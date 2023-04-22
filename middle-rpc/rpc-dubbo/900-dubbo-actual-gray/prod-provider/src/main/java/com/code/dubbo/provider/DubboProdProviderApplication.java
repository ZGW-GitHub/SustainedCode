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

package com.code.dubbo.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Snow
 * @date 2022/7/8 15:24
 */
@Slf4j
@EnableDubbo
@SpringBootApplication
public class DubboProdProviderApplication {
	public static void main(String[] args) {

		// JVM 参数：--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.math=ALL-UNNAMED --add-opens java.base/sun.net.util=ALL-UNNAMED -Ddubbo.provider.parameters.appEnvironment=prod
		new SpringApplicationBuilder(DubboProdProviderApplication.class).run(args);
		log.info("DubboProdProviderApplication 启动成功");

	}
}
