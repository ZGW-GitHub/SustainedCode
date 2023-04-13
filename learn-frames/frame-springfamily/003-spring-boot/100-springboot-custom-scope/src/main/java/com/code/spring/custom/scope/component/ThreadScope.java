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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Snow
 * @date 2022/3/20 19:45
 */
@Slf4j
@SuppressWarnings("all")
public class ThreadScope implements Scope {

	public static final String SCOPE_THREAD = "thread";

	private ThreadLocal<Map<String, Object>> beanMap = new ThreadLocal() {
		@Override
		protected Object initialValue() {
			return new HashMap<>();
		}
	};

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		System.err.println("☆ 调用了自定义 Scope 的 get 方法，name：" + name);
		Object bean = beanMap.get().get(name);
		if (Objects.isNull(bean)) {
			bean = objectFactory.getObject();
			beanMap.get().put(name, bean);
		}
		return bean;
	}

	@Override
	public Object remove(String name) {
		return this.beanMap.get().remove(name);
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
		return Thread.currentThread().getName();
	}

}
