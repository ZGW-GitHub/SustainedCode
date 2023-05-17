package com.code.framework.job.job.abstracts;

import com.code.framework.common.exception.BizException;
import com.code.framework.common.exception.enums.ExceptionCodeEnum;
import com.code.framework.common.trace.context.TraceContextHelper;
import com.code.framework.common.trace.context.TraceContextKeyEnum;
import com.code.framework.job.job.AbstractJob;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author 愆凡
 * @date 2022/6/13 18:10
 */
@Slf4j
public abstract class AbstractThreadPoolJob<T> extends AbstractJob<T> {

	@Override
	protected final void doExecute(List<T> dataList) {
		totalCnt.getAndAdd(dataList.size());

		CompletionService<Boolean> completionService = getCompletionService();
		if (completionService == null) {
			throw new BizException(ExceptionCodeEnum.BAD_XXL_JOB_HANDLER);
		}

		List<Future<Boolean>> futureList = new ArrayList<>();
		dataList.forEach(data -> {
			Future<Boolean> future = completionService.submit(() -> {
				try {
					return handler(data);
				} catch (Exception e) {
					log.error("xxl-job : {}(traceId:{}) ，执行【 handler(data) 】发生异常：{} ，data ：{}", jobClassName,
							TraceContextHelper.getTraceContext().getInfo(TraceContextKeyEnum.UNIQUE_ID), e.getMessage(), data.toString(), e);
					return false;
				}
			});
			futureList.add(future);
		});

		completeFuture(futureList, completionService);
	}

	private void completeFuture(List<Future<Boolean>> futureList, CompletionService<Boolean> completionService) {
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
				log.error("xxl-job : {}(traceId:{}) ，执行【 completionService.take().get() 】发生异常：{}", jobClassName,
						TraceContextHelper.getTraceContext().getInfo(TraceContextKeyEnum.UNIQUE_ID), e.getMessage(), e);
			}
		}
	}

	protected abstract ExecutorCompletionService<Boolean> getCompletionService();

	protected abstract ExecutorService getThreadPoolExecutor();

}
