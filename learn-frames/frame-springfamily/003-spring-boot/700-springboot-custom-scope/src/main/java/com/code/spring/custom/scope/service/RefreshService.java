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

import cn.hutool.core.util.StrUtil;
import com.code.spring.custom.scope.component.annotation.RefreshScopeAnno;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Snow
 * @date 2022/3/20 21:17
 */
@Slf4j
@Getter
@Setter
@Service
@RefreshScopeAnno
public class RefreshService {

	private String serviceId;

	public RefreshService() {
		System.err.println(" - 初始化 RefreshService ，this ：" + this);

		this.serviceId = StrUtil.uuid();
	}

	public String test() {
		return serviceId;
	}

}
