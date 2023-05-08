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

package com.code.spring.boot.configuration;

import cn.hutool.core.map.MapUtil;
import com.code.spring.boot.component.datasource.DynamicDataSource;
import com.code.spring.boot.component.datasource.DynamicDataSourceEnum;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author Snow
 * @date 2023/2/15 21:03
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MultipleDataSourceProperties.class)
public class DataSourceConfiguration {

	@Bean
	public DataSource firstDataSource(MultipleDataSourceProperties properties) {
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.url(properties.getFirstUrl())
				.username(properties.getFirstUsername())
				.password(properties.getFirstPassword())
				.driverClassName(properties.getFirstDriver())
				.build();
	}

	@Bean
	public DataSource secondDataSource(MultipleDataSourceProperties properties) {
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.url(properties.getSecondUrl())
				.username(properties.getSecondUsername())
				.password(properties.getSecondPassword())
				.driverClassName(properties.getSecondDriver())
				.build();
	}

	@Bean
	public DataSource dynamicDataSource(DataSource firstDataSource, DataSource secondDataSource) {
		Map<Object, Object> targetDataSources = MapUtil.builder()
				.put(DynamicDataSourceEnum.DEFAULT, firstDataSource)
				.put(DynamicDataSourceEnum.FIRST, firstDataSource)
				.put(DynamicDataSourceEnum.SECOND, secondDataSource)
				.build();

		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(targetDataSources.get(DynamicDataSourceEnum.DEFAULT));
		return dataSource;
	}

}
