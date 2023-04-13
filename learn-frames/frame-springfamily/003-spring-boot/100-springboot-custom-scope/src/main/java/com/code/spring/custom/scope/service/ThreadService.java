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

package com.code.spring.custom.scope.service;

import com.code.spring.custom.scope.component.ThreadScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author Snow
 * @date 2022/3/20 19:57
 */
@Slf4j
@Service
@Scope(ThreadScope.SCOPE_THREAD)
public class ThreadService {

	public ThreadService() {
		System.err.println(" - 初始化 ThreadService ，线程 ：" + Thread.currentThread());
		System.err.println(" - 初始化 ThreadService ，this ：" + this);
	}

}
