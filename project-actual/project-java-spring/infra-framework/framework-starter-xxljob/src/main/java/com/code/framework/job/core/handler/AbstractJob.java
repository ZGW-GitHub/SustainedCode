package com.code.framework.job.core.handler;

import com.code.framework.common.trace.context.TraceContext;
import com.code.framework.common.trace.context.TraceContextHelper;
import com.code.framework.common.trace.context.TraceContextKeyEnum;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 愆凡
 * @date 2022/6/13 11:46
 */
@Slf4j
public abstract class AbstractJob<T> extends IJobHandler {

	protected String jobClassName = this.getClass().getSimpleName();

	/**
	 * 任务开始时间
	 */
	protected long startTime = 0;
	/**
	 * 任务结束时间
	 */
	protected long endTime   = 0;

	/**
	 * 总数据条数
	 */
	protected AtomicInteger totalCnt   = new AtomicInteger(0);
	/**
	 * 处理成功数据条数
	 */
	protected AtomicInteger successCnt = new AtomicInteger(0);
	/**
	 * 处理失败数据条数
	 */
	protected AtomicInteger failedCnt  = new AtomicInteger(0);

	@Override
	public final void execute() {

		TraceContext traceContext = new TraceContext();
		TraceContextHelper.setTraceContext(traceContext);

		startTime = System.currentTimeMillis();
		log.info("xxl-job : {}(traceId:{}) ，开始执行，开始时间(ms)：{}", jobClassName, traceContext.getInfo(TraceContextKeyEnum.UNIQUE_ID), startTime);

		try {
			// 1、初始化统计计数
			initCnt();

			// 2、查询数据
			List<T> dataList = fetchDataList();

			if (!dataList.isEmpty()) {
				// 3、处理数据
				doExecute(dataList);
			}
		} finally {
			endTime = System.currentTimeMillis();
			log.info("xxl-job : {}(traceId:{}) ，执行结束，执行耗时(ms)：{}，totalCnt：{}，successCnt：{}，failedCnt：{}", jobClassName,
					traceContext.getInfo(TraceContextKeyEnum.UNIQUE_ID), endTime - startTime, totalCnt.get(), successCnt.get(), failedCnt.get());

			TraceContextHelper.clear();
		}
	}

	private void initCnt() {
		totalCnt = new AtomicInteger(0);
		successCnt = new AtomicInteger(0);
		failedCnt = new AtomicInteger(0);
	}

	private List<T> fetchDataList() {
		try {
			List<T> dataList = doFetchDataList();

			return Optional.ofNullable(dataList).orElse(Collections.emptyList());
		} catch (Exception e) {
			log.error("xxl-job : {}(traceId:{}) ，执行【 doFetchDataList() 】发生异常：{}", jobClassName,
					TraceContextHelper.getTraceContext().getInfo(TraceContextKeyEnum.UNIQUE_ID), e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	protected abstract void doExecute(List<T> dataList);

	protected abstract List<T> doFetchDataList();

	protected abstract boolean handler(T data);

}
