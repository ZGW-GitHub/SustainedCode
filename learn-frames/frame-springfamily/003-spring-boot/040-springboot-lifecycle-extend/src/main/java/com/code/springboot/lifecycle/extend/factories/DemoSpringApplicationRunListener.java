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

package com.code.springboot.lifecycle.extend.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

/**
 * @author Snow
 * @date 2021/10/28 09:54
 */
@Slf4j
public class DemoSpringApplicationRunListener implements SpringApplicationRunListener, Ordered {

	private final SpringApplication application;

	private final String[] args;

	public DemoSpringApplicationRunListener(SpringApplication application, String[] args) {
		this.application = application;
		this.args = args;

		System.err.println("自定义 SpringApplicationRunListener 的 构造函数 被调用, 入参 : " + application.getClass().getName() + "、" + args.toString());
	}

	@Override
	public void starting(ConfigurableBootstrapContext bootstrapContext) {
		System.err.println("自定义 SpringApplicationRunListener 的 starting 方法被调用");
	}

	@Override
	public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
		System.err.println("自定义 SpringApplicationRunListener 的 environmentPrepared 方法被调用");
	}

	@Override
	public void started(ConfigurableApplicationContext context, Duration timeTaken) {
		System.err.println("自定义 SpringApplicationRunListener 的 started 方法被调用");
	}

	@Override
	public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
		System.err.println("自定义 SpringApplicationRunListener 的 ready 方法被调用");
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		System.err.println("自定义 SpringApplicationRunListener 的 contextPrepared 方法被调用");
	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {
		System.err.println("自定义 SpringApplicationRunListener 的 contextLoaded 方法被调用");
	}

	@Override
	public void started(ConfigurableApplicationContext context) {
		System.err.println("自定义 SpringApplicationRunListener 的 started 方法被调用");
	}

	@Override
	public void running(ConfigurableApplicationContext context) {
		System.err.println("自定义 SpringApplicationRunListener 的 running 方法被调用");
	}

	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		System.err.println("自定义 SpringApplicationRunListener 的 failed 方法被调用");
	}

	@Override
	public int getOrder() {
		return -100;
	}
}
