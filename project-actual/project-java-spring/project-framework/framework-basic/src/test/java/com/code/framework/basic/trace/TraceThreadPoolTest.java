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
import com.code.framework.basic.trace.thread.TraceThreadPoolExecutor;
import com.code.framework.basic.util.MDCUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Snow
 * @date 2023/6/9 19:41
 */
@Slf4j
public class TraceThreadPoolTest extends BasicFrameworkTest {

	private final ExecutorService executorService = new TraceThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

	@BeforeAll
	static void before() {
		TraceContext traceContext = TraceContextHelper.startTrace();
		traceContext.addInfo(TraceContextKeyEnum.TRACE_ID, IdUtil.fastSimpleUUID());

		MDCUtil.setTraceId(TraceContextHelper.getTraceId());
	}

	@AfterAll
	static void after() {
		MDCUtil.removeTraceId();
	}

	@Test
	@SneakyThrows
	void demoTest() {
		log.info("准备提交任务到线程池，hasTraceContext : {}", TraceContextHelper.hasTraceContext());

		executorService.execute(() -> {
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

	/**
	 * TraceExecutorCompletionService 链路追踪测试
	 */
	@Test
	@SneakyThrows
	void completionServiceTraceTest() {
		CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executorService);

		log.info("准备提交任务到线程池，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
		completionService.submit(() -> {
			log.info("开始执行任务，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			log.info("执行任务结束，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
			return true;
		});

		Boolean result = completionService.take().get();
		System.err.println(result);

		log.info("结束，hasTraceContext : {}", TraceContextHelper.hasTraceContext());
	}

	/**
	 * TraceExecutorCompletionService 耗时测试
	 */
	@Test
	void completionServiceTimeTest() throws InterruptedException, ExecutionException {
		CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);

		final List<Future<Integer>> futures = new ArrayList<>();
		futures.add(completionService.submit(() -> task(60)));
		futures.add(completionService.submit(() -> task(6)));
		futures.add(completionService.submit(() -> task(4)));
		futures.add(completionService.submit(() -> task(2)));

		long before = System.currentTimeMillis();
		// 遍历 Future list，通过 get() 方法获取每个 future 结果
		for (Future<Integer> ignored : futures) {
			Integer result = completionService.take().get();

			System.out.println(result);
		}
		// 32+30+30+30=122
		System.err.println((System.currentTimeMillis() - before) / 1000);
	}

	@Test
	@SneakyThrows
	void completableFutureTest() {
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
