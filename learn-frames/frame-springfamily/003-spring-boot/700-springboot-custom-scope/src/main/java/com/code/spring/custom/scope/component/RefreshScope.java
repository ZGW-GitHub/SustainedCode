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

package com.code.spring.custom.scope.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Snow
 * @date 2022/3/20 19:45
 */
@Slf4j
@SuppressWarnings("all")
public class RefreshScope implements Scope {

	public static final String SCOPE_REFRESH = "refresh";

	private static final RefreshScope INSTANCE = new RefreshScope();

	// 来个 map 用来缓存 Bean
	private ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>();

	public static RefreshScope getInstance() {
		return INSTANCE;
	}

	/**
	 * 清空 map 中所有的 Bean
	 */
	public static void clean() {
		INSTANCE.beanMap.clear();
	}

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		System.err.println("☆ 调用了自定义 Scope 的 get 方法，name：" + name);

		Object bean = beanMap.get(name);
		if (bean == null) {
			bean = objectFactory.getObject();
			System.err.println("☆ objectFactory : " + objectFactory + "，bean ：" + bean);
			beanMap.put(name, bean);
		}
		return bean;
	}

	@Override
	public Object remove(String name) {
		return beanMap.remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		// TODO
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return "ContextId";
	}

}
