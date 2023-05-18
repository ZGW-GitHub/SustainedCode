package com.code.service.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author 愆凡
 * @date 2022/6/12 17:38
 */
@Slf4j
@EnableDubbo
@SpringBootApplication
public class ServiceTemplateApplication {
	public static void main(String[] args) {

		new SpringApplicationBuilder(ServiceTemplateApplication.class).run(args);

	}
}