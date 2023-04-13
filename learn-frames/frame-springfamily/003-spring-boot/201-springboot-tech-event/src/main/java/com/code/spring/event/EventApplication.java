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

package com.code.spring.event;

import com.code.spring.event.component.event.DemoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Snow
 * @date 2022/2/28 21:13
 */
@Slf4j
@SpringBootApplication
public class EventApplication {
	public static void main(String[] args) throws InterruptedException {

		ConfigurableApplicationContext context = new SpringApplicationBuilder(EventApplication.class).run(args);

		log.info("开始发布自定义事件");

		// 构造事件
		DemoEvent customApplicationEvent = new DemoEvent(context, "测试事件");

		// 发布事件
		context.publishEvent(customApplicationEvent);

		log.info("发布自定义事件结束");

		Thread.currentThread().join();

	}
}
