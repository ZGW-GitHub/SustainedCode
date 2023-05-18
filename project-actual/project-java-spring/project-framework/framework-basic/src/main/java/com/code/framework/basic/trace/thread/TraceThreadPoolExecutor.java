package com.code.framework.basic.trace.thread;

import cn.hutool.core.util.IdUtil;
import com.code.framework.basic.trace.context.TraceContext;
import com.code.framework.basic.trace.context.TraceContextHelper;
import com.code.framework.basic.trace.context.TraceContextKeyEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author 愆凡
 * @date 2022/6/14 10:13
 */
@Slf4j
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {

	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
								   ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}

	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
								   RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}

	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
								   ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
		return new TraceFutureTask<>(runnable, value);
	}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		return new TraceFutureTask<>(callable);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		TraceFutureTask<?> task = (TraceFutureTask<?>) r;
		TraceContext mainThreadTraceContext = task.getMainThreadTraceContext();
		TraceContext subThreadTraceContext = new TraceContext(mainThreadTraceContext);

		subThreadTraceContext.addInfo(TraceContextKeyEnum.ASYNC_TASK_ID, IdUtil.fastSimpleUUID());
		TraceContextHelper.setTraceContext(subThreadTraceContext);

		super.beforeExecute(t, r);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		TraceContextHelper.clear();

		super.afterExecute(r, t);
	}

}
