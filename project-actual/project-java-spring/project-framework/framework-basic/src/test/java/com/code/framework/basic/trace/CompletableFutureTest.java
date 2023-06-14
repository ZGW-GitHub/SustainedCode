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

package com.code.framework.basic.trace;

import com.code.framework.basic.trace.thread.TraceThreadPoolExecutor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2023/6/14 14:40
 */
@Slf4j
public class CompletableFutureTest {

	private final ExecutorService executorService = new TraceThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

	@Test
	@SneakyThrows
	void demo() {
		CompletableFuture.supplyAsync(() -> task(3), executorService).whenComplete((result, e) -> System.err.println(result));

		TimeUnit.SECONDS.sleep(5);
	}

	public Integer task(Integer sleep) {
		try {
			log.info("执行睡眠：{}", sleep);
			TimeUnit.SECONDS.sleep(sleep);
		} catch (Throwable e) {
			log.error("发生异常 ：", e);
		}

		return sleep;
	}

}
