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


import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Snow
 * @date 2020/5/23 10:21 上午
 */
public class UseTest {

	public final CompletableFuture<Integer> futureData       = CompletableFuture.supplyAsync(TaskUtil::getData);
	public final CompletableFuture<String>  futureStringData = CompletableFuture.supplyAsync(TaskUtil::getStringData);
	public final CompletableFuture<Integer> futureException  = CompletableFuture.supplyAsync(TaskUtil::throwException);

	/**
	 * {@link CompletableFuture#whenComplete} 对 CompletableFuture 的结果进行消费
	 */
	@Test
	void whenCompleteTestOne() throws ExecutionException, InterruptedException {
//		CompletableFuture<Integer> future = futureException.whenComplete((result, exception) -> {
		CompletableFuture<Integer> future = futureData.whenComplete((result, exception) -> {
			System.out.println("Result : " + result);
			System.out.println("Exception : " + (exception == null ? "无异常" : exception.getClass()));
		}).exceptionally(exception -> {
			System.out.println("进行异常的处理...");
			return 6;
		});

		System.out.println(futureData.get()); // 100
		System.out.println(future.get()); // 100 、6
	}

	@Test
	void whenCompleteTestTwo() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> future1 = futureException.whenComplete((result, exception) -> {
			System.out.println("Result : " + result);
			System.out.println("Exception : " + (exception == null ? "无异常" : exception.getClass()));
		});

		CompletableFuture<Integer> future2 = future1.exceptionally(exception -> {
			System.out.println("进行异常的处理...");
			return 6;
		});

		System.out.println(future2.get()); // 6
	}

	@Test
	void thenApplyTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> future = futureData.thenApply((result) -> {
			System.out.println("执行到 handle 了，result : " + result);
			return result + 100;
		});

		System.out.println("计算结果：" + future.get()); // 200
		System.out.println("计算结果：" + futureData.get()); // 100
	}

	@Test
	void handleTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> future = futureData.handle((result, exception) -> {
			System.out.println("Result : " + result);
			System.out.println("Exception : " + (exception == null ? "无异常" : exception.getClass()));
			return result + 100;
		});

		System.out.println("计算结果：" + future.get()); // 200
		System.out.println("计算结果：" + futureData.get()); // 100
	}

	@Test
	void thenComposeTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);

		CompletableFuture<String> f = future.thenCompose(i -> CompletableFuture.supplyAsync(() -> (i * 10) + ""));

		System.out.println(f.get()); // 1000
	}

	@Test
	void thenCombineTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);

		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "abc");

		CompletableFuture<String> f = future.thenCombine(future2, (x, y) -> y + "-" + x);

		System.out.println(f.get()); // abc-100
	}

	@Test
	void thenAcceptTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Void> future = futureData
				.thenAccept((result) -> System.out.println("Result : " + result));

		System.out.println("计算结果：" + future.get()); // null
		System.out.println("计算结果：" + this.futureData.get()); // 100
	}

	@Test
	void thenAcceptBothTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Void> completableFuture3 = futureData
				.thenAcceptBoth(futureStringData, (result1, result2) -> System.out.println("Result : " + result1 + result2));

		System.out.println("计算结果：" + futureData.get()); // 100
		System.out.println("计算结果：" + futureStringData.get()); // 100
		System.out.println("计算结果：" + completableFuture3.get()); // null
	}

}
