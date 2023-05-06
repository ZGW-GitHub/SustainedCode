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

package com.code.spring.boot.component.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2023/5/5 21:16
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum DynamicDataSourceEnum {

	FIRST("FIRST", "第一个数据源"),
	SECOND("SECOND", "第二个数据源"),
	DEFAULT(FIRST.name, "默认数据源"),
	;

	private final String name;
	private final String info;

}
