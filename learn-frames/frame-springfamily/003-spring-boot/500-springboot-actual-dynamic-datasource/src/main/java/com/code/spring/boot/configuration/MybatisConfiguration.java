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

import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Snow
 * @date 2023/2/15 21:03
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MultipleDataSourceProperties.class)
@MapperScan(basePackages = "com.code.spring.boot.dal.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfiguration {

	@Resource
	private DataSource dynamicDataSource;

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dynamicDataSource);

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		factoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
		return factoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dynamicDataSource);
		return transactionManager;
	}

}
