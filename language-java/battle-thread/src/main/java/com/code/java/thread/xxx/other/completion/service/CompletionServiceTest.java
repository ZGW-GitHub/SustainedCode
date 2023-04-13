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

package com.code.java.thread.xxx.other.completion.service;

import com.code.java.thread.ExceptionUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2020/5/11 9:14 上午
 */
@SuppressWarnings("all")
public class CompletionServiceTest {

	private final ExecutorService            executorService   = Executors.newFixedThreadPool(4);
	private final CompletionService<Integer> completionService = new ExecutorCompletionService(executorService);

	/**
	 * 直接 submit 到 ExecutorService
	 */
	@Test
	void executorServiceTest() throws ExecutionException, InterruptedException {
		final List<Future<Integer>> futures = new ArrayList<>();
		futures.add(executorService.submit(() -> task(60)));
		futures.add(executorService.submit(() -> task(6)));
		futures.add(executorService.submit(() -> task(4)));
		futures.add(executorService.submit(() -> task(2)));

		long before = System.currentTimeMillis();
		// 遍历 Future list，通过 get() 方法获取每个 future 结果
		for (Future<Integer> future : futures) {
			Integer result = future.get();
			TimeUnit.SECONDS.sleep(30);

			System.out.println(result);
		}
		// 60+30+30+30+30=180
		System.err.println((System.currentTimeMillis() - before) / 1000);
	}

	/**
	 * submit 到 CompletionService
	 */
	@Test
	void completionServiceTest() throws InterruptedException, ExecutionException {
		final List<Future<Integer>> futures = new ArrayList<>();
		futures.add(completionService.submit(() -> task(60)));
		futures.add(completionService.submit(() -> task(6)));
		futures.add(completionService.submit(() -> task(4)));
		futures.add(completionService.submit(() -> task(2)));

		long before = System.currentTimeMillis();
		// 遍历 Future list，通过 get() 方法获取每个 future 结果
		for (Future<Integer> ignored : futures) {
			Integer result = completionService.take().get();
			TimeUnit.SECONDS.sleep(30);

			System.out.println(result);
		}
		// 32+30+30+30=122
		System.err.println((System.currentTimeMillis() - before) / 1000);
	}

	public Integer task(Integer sleep) {
		ExceptionUtil.processorVoid(() -> TimeUnit.SECONDS.sleep(sleep));

		return sleep;
	}

}
