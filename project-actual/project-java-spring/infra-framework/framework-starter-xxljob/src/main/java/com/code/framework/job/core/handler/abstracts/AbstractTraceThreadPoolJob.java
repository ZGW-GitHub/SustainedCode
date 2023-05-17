package com.code.framework.job.core.handler.abstracts;

import com.code.framework.common.exception.BizException;
import com.code.framework.common.exception.enums.ExceptionCodeEnum;
import com.code.framework.common.trace.context.TraceContext;
import com.code.framework.common.trace.context.TraceContextHelper;
import com.code.framework.common.trace.context.TraceContextKeyEnum;
import com.code.framework.common.trace.thread.TraceFutureTask;
import com.code.framework.common.trace.thread.TraceThreadPoolExecutor;
import com.code.framework.job.core.handler.AbstractJob;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;

/**
 * @author 愆凡
 * @date 2022/6/14 10:41
 */
@Slf4j
public abstract class AbstractTraceThreadPoolJob<T> extends AbstractJob<T> {

	@Override
	protected void doExecute(List<T> dataList) {
		totalCnt.getAndAdd(dataList.size());

		ExecutorCompletionService<Boolean> completionService = getCompletionService();
		if (completionService == null) {
			throw new BizException(ExceptionCodeEnum.BAD_XXL_JOB_HANDLER);
		}

		List<TraceFutureTask<Boolean>> futureList = new ArrayList<>();
		for (final T data : dataList) {
			TraceFutureTask<Boolean> future = (TraceFutureTask<Boolean>) completionService.submit(() -> {
				try {
					return handler(data);
				} catch (Exception e) {
					TraceContext traceContext = TraceContextHelper.getTraceContext();
					log.error("xxl-job : {}(traceId:{}、taskId:{}) ，执行【 handler(data) 】发生异常：{} ，data ：{}", jobClassName,
							traceContext.getInfo(TraceContextKeyEnum.UNIQUE_ID), traceContext.getInfo(TraceContextKeyEnum.ASYNC_TASK_ID), e.getMessage(),
							data.toString(), e);
					return false;
				}
			});
			futureList.add(future);
		}

		completeFuture(futureList, completionService);
	}

	private void completeFuture(List<TraceFutureTask<Boolean>> futureList, CompletionService<Boolean> completionService) {
		for (int i = 0; i < futureList.size(); i++) {
			try {
				Boolean success = completionService.take().get();
				if (success) {
					successCnt.getAndIncrement();
				} else {
					failedCnt.getAndIncrement();
				}
			} catch (Exception e) {
				failedCnt.getAndIncrement();

				TraceContext traceContext = TraceContextHelper.getTraceContext();
				log.error("xxl-job : {}(traceId:{}、taskId:{}) ，执行【 completionService.take().get() 】发生异常：{}", jobClassName,
						traceContext.getInfo(TraceContextKeyEnum.UNIQUE_ID), traceContext.getInfo(TraceContextKeyEnum.ASYNC_TASK_ID), e.getMessage(), e);
			}
		}
	}

	protected abstract ExecutorCompletionService<Boolean> getCompletionService();

	protected abstract TraceThreadPoolExecutor getThreadPoolExecutor();

}
