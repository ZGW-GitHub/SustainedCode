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

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * @author Snow
 * @date 2022/11/26 22:48
 */
@Slf4j
public class CreateTest {

	@Test
	void simpleTest() {
		CompletableFuture<String> future = new CompletableFuture<>();

//		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "test");
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "test", Executors.newFixedThreadPool(10));

//		CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> System.out.println("test"));
		CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> System.out.println("test"), Executors.newFixedThreadPool(10));

		CompletableFuture<String> future3 = CompletableFuture.completedFuture("test");

		CompletableFuture<Object> future4 = CompletableFuture.failedFuture(new RuntimeException());
	}

}
