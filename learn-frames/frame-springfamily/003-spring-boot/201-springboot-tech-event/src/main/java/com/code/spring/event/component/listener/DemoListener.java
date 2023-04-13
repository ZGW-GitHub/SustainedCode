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

package com.code.spring.event.component.listener;

import com.code.spring.event.component.event.DemoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Snow
 * @date 2022/2/28 21:30
 */
@Slf4j
@Component
public class DemoListener {

	@Async("threadPoolOne") // 异步处理 Event
	@EventListener(DemoEvent.class)
	public void listenerOne(DemoEvent event) {
		log.info("执行任务的线程：{}", Thread.currentThread().getName());
		log.info("EventListener - One 注解方式接收到的消息为: {}", event.getEventContent());
	}

	@Async("threadPoolTwo") // 异步处理 Event
	@EventListener(DemoEvent.class)
	public void listenerTwo(DemoEvent event) {
		log.info("执行任务的线程：{}", Thread.currentThread().getName());
		log.info("EventListener - Two 注解方式接收到的消息为: {}", event.getEventContent());
	}

	@Async("threadPoolThree") // 异步处理 Event
	@EventListener(DemoEvent.class)
	public void listenerThree(DemoEvent event) {
		log.info("执行任务的线程：{}", Thread.currentThread().getName());
		log.info("EventListener - Three 注解方式接收到的消息为: {}", event.getEventContent());
	}

}
