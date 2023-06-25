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

package com.code.framework.xxl.job.job;

import com.code.framework.basic.trace.context.TraceContextHelper;
import com.code.framework.basic.trace.context.TraceContextKeyEnum;
import com.code.framework.basic.util.IdGenerator;
import com.code.framework.basic.util.MDCUtil;
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
public abstract class AbstractJob<D> extends IJobHandler {

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
		TraceContextHelper.startTrace();
		TraceContextHelper.addInfo(TraceContextKeyEnum.JOB_ID, IdGenerator.jobId());
		MDCUtil.setTraceId(TraceContextHelper.getInfo(TraceContextKeyEnum.JOB_ID));

		startTime = System.currentTimeMillis();
		log.info("xxl-job : {}，开始执行，开始时间(ms)：{}", jobClassName, startTime);

		try {
			// 1、初始化统计计数
			initCnt();

			// 2、查询数据
			List<D> dataList = fetchData();

			if (!dataList.isEmpty()) {
				// 3、处理数据
				doExecute(dataList);
			}
		} catch (Exception e) {
			log.error("xxl-job : {}，执行【 execute() 】发生异常：{}", jobClassName, e.getMessage(), e);
		} finally {
			endTime = System.currentTimeMillis();
			log.info("xxl-job : {}，执行结束，执行耗时(ms)：{}，totalCnt：{}，successCnt：{}，failedCnt：{}", jobClassName, endTime - startTime, totalCnt.get(), successCnt.get(), failedCnt.get());

			TraceContextHelper.clear();
			MDCUtil.clear();
		}
	}

	private void initCnt() {
		totalCnt = new AtomicInteger(0);
		successCnt = new AtomicInteger(0);
		failedCnt = new AtomicInteger(0);
	}

	private List<D> fetchData() {
		try {
			List<D> dataList = doFetchData();

			return Optional.ofNullable(dataList).orElse(Collections.emptyList());
		} catch (Exception e) {
			log.error("xxl-job : {}，执行【 doFetchDataList() 】发生异常：{}", jobClassName, e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	protected abstract void doExecute(List<D> dataList);

	protected abstract List<D> doFetchData();

	protected abstract boolean handler(D data);

}
