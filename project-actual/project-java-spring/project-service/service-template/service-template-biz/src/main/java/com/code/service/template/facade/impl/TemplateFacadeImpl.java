package com.code.service.template.facade.impl;

import com.code.service.template.facade.TemplateFacade;
import com.code.service.template.mvc.service.TemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author 愆凡
 * @date 2022/6/12 18:38
 */
@Slf4j
@DubboService(version = "1.0")
public class TemplateFacadeImpl implements TemplateFacade {

	@Resource
	private TemplateService templateService;

	@Override
	public void test() {

	}

}
