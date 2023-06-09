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

package com.code.framework.xxl.job;

import com.code.framework.xxl.job.config.XxlJobConfig;
import com.code.framework.xxl.job.config.XxlJobExecutorConfig;
import com.code.framework.xxl.job.job.AbstractJob;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 愆凡
 * @date 2022/6/14 14:34
 */
@Slf4j
@Setter
@Configuration
@ComponentScan("com.code.framework.xxl.job")
public class XxlJobFrameworkAutoConfiguration implements SmartInitializingSingleton, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Resource
	private XxlJobConfig xxlJobConfig;

	@Resource
	private XxlJobExecutorConfig xxlJobExecutorConfig;

	@Bean
	public XxlJobSpringExecutor xxlJobExecutor() {
		XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
		executor.setAdminAddresses(xxlJobConfig.getAddress());
		executor.setPort(xxlJobExecutorConfig.getPort());
		executor.setAppname(xxlJobExecutorConfig.getAppname());
		executor.setLogPath(xxlJobExecutorConfig.getLogPath());
		executor.setLogRetentionDays(xxlJobExecutorConfig.getLogRetentionDays());

		log.info(">>>>>>>>>>> xxl-job config init : {}", xxlJobExecutorConfig);
		return executor;
	}

	/**
	 * 将定义 Job 注册到 XXL-JOB
	 */
	@Override
	public void afterSingletonsInstantiated() {
		applicationContext.getBeansOfType(AbstractJob.class).forEach((handlerName, handler) -> {
			log.info(">>>>>>>>>>> xxl-job register handler : {}", handlerName);

			XxlJobExecutor.registJobHandler(handlerName, handler);
		});
	}

}
