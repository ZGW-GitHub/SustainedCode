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

package com.code.framework.basic.trace.thread;

import java.util.concurrent.*;

/**
 * @author Snow
 * @date 2023/6/12 11:34
 */
public class TraceExecutorCompletionService<V> implements CompletionService<V> {

	private final TraceThreadPoolExecutor  executor;
	private final BlockingQueue<Future<V>> completionQueue;

	public static class QueueingFuture<V> extends TraceFutureTask<Void> {

		private final Future<V>                task;
		private final BlockingQueue<Future<V>> completionQueue;

		QueueingFuture(RunnableFuture<V> task, BlockingQueue<Future<V>> completionQueue) {
			super(task, null);
			this.task = task;
			this.completionQueue = completionQueue;
		}

		protected void done() {
			completionQueue.add(task);
		}
	}

	private RunnableFuture<V> newTaskFor(Callable<V> task) {
		return executor.newTaskFor(task);
	}

	private RunnableFuture<V> newTaskFor(Runnable task, V result) {
		return executor.newTaskFor(task, result);
	}

	public TraceExecutorCompletionService(Executor executor) {
		if (executor == null) {
			throw new NullPointerException();
		}
		if (!(executor instanceof TraceThreadPoolExecutor)) {
			throw new RuntimeException("不支持的 Executor 类型");
		}
		this.executor = (TraceThreadPoolExecutor) executor;
		this.completionQueue = new LinkedBlockingQueue<>();
	}

	public TraceExecutorCompletionService(Executor executor, BlockingQueue<Future<V>> completionQueue) {
		if (executor == null || completionQueue == null) {
			throw new NullPointerException();
		}
		if (!(executor instanceof TraceThreadPoolExecutor)) {
			throw new RuntimeException("不支持的 Executor 类型");
		}
		this.executor = (TraceThreadPoolExecutor) executor;
		this.completionQueue = completionQueue;
	}

	public Future<V> submit(Callable<V> task) {
		if (task == null) throw new NullPointerException();
		RunnableFuture<V> f = newTaskFor(task);
		executor.execute(new QueueingFuture<>(f, completionQueue));
		return f;
	}

	public Future<V> submit(Runnable task, V result) {
		if (task == null) throw new NullPointerException();
		RunnableFuture<V> f = newTaskFor(task, result);
		executor.execute(new QueueingFuture<>(f, completionQueue));
		return f;
	}

	public Future<V> take() throws InterruptedException {
		return completionQueue.take();
	}

	public Future<V> poll() {
		return completionQueue.poll();
	}

	public Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException {
		return completionQueue.poll(timeout, unit);
	}

}
