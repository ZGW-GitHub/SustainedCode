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

package com.code.mybatis.component.datasource;

/**
 * @author Snow
 * @date 2023/5/5 15:35
 */
public class DataSourceContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/**
	 * 设置数据源类型
	 *
	 * @param dataSourceType 数据库类型
	 */
	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	/**
	 * 获取数据源类型
	 */
	public static String getDataSourceType() {
		return contextHolder.get();
	}

	/**
	 * 清除数据源类型
	 */
	public static void clearDataSourceType() {
		contextHolder.remove();
	}

}
