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

package com.code.java.thread.vvv.task.layout.completable.future;

import com.code.java.thread.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Snow
 * @date 2023/6/9 09:01
 */
@Slf4j
public class TimeConsumeTest {

	private final ExecutorService executorService = Executors.newFixedThreadPool(4);

	@Test
	public void test1() throws ExecutionException, InterruptedException {
		final List<CompletableFuture<Integer>> futures = new ArrayList<>();
		futures.add(CompletableFuture.supplyAsync(() -> task(60), executorService));
		futures.add(CompletableFuture.supplyAsync(() -> task(6), executorService));
		futures.add(CompletableFuture.supplyAsync(() -> task(4), executorService));
		futures.add(CompletableFuture.supplyAsync(() -> task(2), executorService));

		long before = System.currentTimeMillis();
		// 遍历 Future list，通过 get() 方法获取每个 future 结果
		for (Future<Integer> future : futures) {
			Integer result = future.get();

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
