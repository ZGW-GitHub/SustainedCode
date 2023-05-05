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

import cn.hutool.core.map.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author Snow
 * @date 2023/4/16 20:37
 */
public class SnowDataSource extends AbstractRoutingDataSource {

	@Resource
	private DataSource firstDataSource;

	@Resource
	private DataSource secondDataSource;

	@Override
	protected Object determineCurrentLookupKey() {
		return null;
	}

	@Override
	public void afterPropertiesSet() {
		Map<Object, Object> targetDataSources = MapUtil.builder().put("first", firstDataSource).put("second", secondDataSource).build();
		setTargetDataSources(targetDataSources);
		setDefaultTargetDataSource(firstDataSource);

		super.afterPropertiesSet();
	}

}
