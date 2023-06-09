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

package com.code.service.template.job;

import com.code.framework.xxl.job.job.ThreadPoolJob;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author Snow
 * @date 2023/6/9 22:42
 */
@Slf4j
public class DemoThreadJob extends ThreadPoolJob<Object> {

	@Override
	protected List<Object> doFetchData() {
		return null;
	}

	@Override
	protected boolean handler(Object data) {
		return false;
	}

	@Override
	protected Executor getThreadPoolExecutor() {
		return null;
	}

}
