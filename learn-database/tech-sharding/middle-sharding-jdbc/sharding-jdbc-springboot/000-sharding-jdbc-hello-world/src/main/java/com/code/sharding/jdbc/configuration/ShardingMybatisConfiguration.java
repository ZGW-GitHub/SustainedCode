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

package com.code.sharding.jdbc.configuration;

import cn.hutool.core.bean.BeanUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Snow
 * @date 2023/2/25 22:03
 */
@Configuration(proxyBeanMethods = false)
@MapperScan(basePackages = "com.code.sharding.jdbc.dal.sharding.mapper", sqlSessionFactoryRef = "shardingSqlSessionFactory")
public class ShardingMybatisConfiguration {

	@Bean
	@ConfigurationProperties("jdbc.sharding")
	public DataSource shardingDataSource() {
		return new HikariDataSource();
	}

	@Bean
	public SqlSessionFactoryBean shardingSqlSessionFactory(DataSource shardingDataSource, org.apache.ibatis.session.Configuration configuration) throws IOException {
		configuration = BeanUtil.copyProperties(configuration, org.apache.ibatis.session.Configuration.class);

		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(shardingDataSource);
		factoryBean.setConfiguration(configuration);

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		factoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/sharding/*.xml"));
		return factoryBean;
	}

	@Bean
	public DataSourceTransactionManager shardingDsTransactionManager(DataSource shardingDataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(shardingDataSource);
		return transactionManager;
	}

}
