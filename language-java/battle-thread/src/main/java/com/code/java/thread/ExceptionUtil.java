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

package com.code.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author Snow
 * @date 2022/5/13 10:41
 */
@Slf4j
public class ExceptionUtil {

	public static void processorVoid(CustomRunnable runnable) {
		processorVoid(runnable, "发生了异常，异常信息");
	}

	public static void processorVoid(CustomRunnable runnable, String msg) {
		try {
			runnable.run();
		} catch (Throwable e) {
			log.error(msg + "：", e);
		}
	}

	public static <V> V processorReturn(Callable<V> callable) {
		return processorReturn(callable, "发生了异常，异常信息");
	}

	public static <V> V processorReturn(Callable<V> callable, String msg) {
		try {
			return callable.call();
		} catch (Throwable e) {
			log.error(msg + "：", e);
			return null;
		}
	}

}
