package com.code.framework.job.configuration;

import com.code.framework.job.core.handler.AbstractJob;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job 自动配置，当 BeanFactory 中存在 AbstractJob 类型的 Bean 时才配置 xxl-job
 *
 * @author 愆凡
 * @date 2022/6/14 14:34
 */
@Slf4j
@Setter
@Configuration
@ConditionalOnBean(AbstractJob.class)
public class XxlJobFrameworkAutoConfiguration implements SmartInitializingSingleton, ApplicationContextAware {

	@Value("${xxl-job.admin.addresses}")
	private String adminAddress;

	private ApplicationContext applicationContext;

	@Bean
	@ConfigurationProperties("xxl-job.executor")
	public XxlJobSpringExecutor xxlJobExecutor() {
		log.info(">>>>>>>>>>> xxl-job config init");

		XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
		executor.setAdminAddresses(adminAddress);
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
