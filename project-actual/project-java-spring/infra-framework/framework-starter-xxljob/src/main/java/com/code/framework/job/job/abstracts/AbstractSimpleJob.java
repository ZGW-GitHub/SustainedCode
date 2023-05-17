package com.code.framework.job.job.abstracts;

import com.code.framework.common.trace.context.TraceContextHelper;
import com.code.framework.common.trace.context.TraceContextKeyEnum;
import com.code.framework.job.job.AbstractJob;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 愆凡
 * @date 2022/6/13 18:03
 */
@Slf4j
public abstract class AbstractSimpleJob<T> extends AbstractJob<T> {

	@Override
	protected final void doExecute(List<T> dataList) {
		totalCnt.getAndAdd(dataList.size());

		dataList.forEach(data -> {
			try {
				boolean success = handler(data);
				if (success) {
					successCnt.getAndIncrement();
				} else {
					failedCnt.getAndIncrement();
				}
			} catch (Exception e) {
				failedCnt.getAndIncrement();
				log.error("xxl-job : {}(traceId:{}) ，执行【 handler(data) 】发生异常：{} ，data ：{}", jobClassName,
						TraceContextHelper.getTraceContext().getInfo(TraceContextKeyEnum.UNIQUE_ID), e.getMessage(), data.toString(), e);
			}
		});
	}

}
