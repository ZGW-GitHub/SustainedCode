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

package com.code.spring.boot.component.datasource;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Snow
 * @date 2023/5/5 15:35
 */
public final class DataSourceContextHolder {

	private static final ThreadLocal<Deque<String>> DATA_SOURCE_HOLDER = new NamedThreadLocal<>("dynamic-datasource") {
		@Override
		protected Deque<String> initialValue() {
			return new ArrayDeque<>();
		}
	};

	/**
	 * 获得当前线程数据源
	 *
	 * @return 数据源名称
	 */
	public static String peek() {
		return DATA_SOURCE_HOLDER.get().peek();
	}

	/**
	 * 设置当前线程数据源
	 * <p>
	 * 如非必要不要手动调用，调用后确保最终清除
	 * </p>
	 *
	 * @param ds 数据源名称
	 */
	public static String push(String ds) {
		String dataSourceStr = StringUtils.isEmpty(ds) ? "" : ds;
		DATA_SOURCE_HOLDER.get().push(dataSourceStr);
		return dataSourceStr;
	}

	/**
	 * 清空当前线程数据源
	 * <p>
	 * 如果当前线程是连续切换数据源 只会移除掉当前线程的数据源名称
	 * </p>
	 */
	public static void poll() {
		Deque<String> deque = DATA_SOURCE_HOLDER.get();
		deque.poll();
		if (deque.isEmpty()) {
			DATA_SOURCE_HOLDER.remove();
		}
	}

	/**
	 * 强制清空本地线程
	 * <p>
	 * 防止内存泄漏，如手动调用了push可调用此方法确保清除
	 * </p>
	 */
	public static void clear() {
		DATA_SOURCE_HOLDER.remove();
	}

}
