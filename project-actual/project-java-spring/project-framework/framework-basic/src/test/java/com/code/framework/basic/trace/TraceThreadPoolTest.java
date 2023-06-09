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

import cn.hutool.core.util.IdUtil;
import com.code.framework.basic.BasicFrameworkTest;
import com.code.framework.basic.trace.context.TraceContext;
import com.code.framework.basic.trace.context.TraceContextHelper;
import com.code.framework.basic.trace.context.TraceContextKeyEnum;
import com.code.framework.basic.trace.log.MDCUtil;
import com.code.framework.basic.trace.thread.TraceThreadPoolExecutor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2023/6/9 19:41
 */
@Slf4j
public class TraceThreadPoolTest extends BasicFrameworkTest {

	private final ExecutorService executorService = new TraceThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

	@Test
	@SneakyThrows
	void demoTest() {
		TraceContext traceContext = TraceContextHelper.startTrace();
		traceContext.addInfo(TraceContextKeyEnum.TRACE_ID, IdUtil.fastSimpleUUID());

		MDCUtil.setTraceId(TraceContextHelper.getTraceId());

		log.info("准备提交任务到线程池，hasTraceContext : {}", TraceContextHelper.hasTraceContext());

		executorService.submit(() -> {
			log.info("开始执行任务，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			log.info("执行任务结束，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
		});

		TimeUnit.SECONDS.sleep(5);

		log.info("结束，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
	}

	@Test
	@SneakyThrows
	void exceptionTest() {
		TraceContext traceContext = TraceContextHelper.startTrace();
		traceContext.addInfo(TraceContextKeyEnum.TRACE_ID, IdUtil.fastSimpleUUID());

		MDCUtil.setTraceId(TraceContextHelper.getTraceId());

		log.info("准备提交任务到线程池，hasTraceContext : {}", TraceContextHelper.hasTraceContext());

		executorService.submit(() -> {
			log.info("开始执行任务，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			throw new RuntimeException("发生异常");

			// log.info("执行任务结束，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
		});

		TimeUnit.SECONDS.sleep(5);

		log.info("结束，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
	}

}
