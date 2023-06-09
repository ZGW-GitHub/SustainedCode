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

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 愆凡
 * @date 2022/6/13 18:03
 */
@Slf4j
public abstract class SimpleJob<T> extends AbstractJob<T> {

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
				log.error("xxl-job : {}，执行【 handler(data) 】发生异常：{} ，data ：{}", jobClassName, e.getMessage(), data.toString(), e);
			}
		});
	}

}
