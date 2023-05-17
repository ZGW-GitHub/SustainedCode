package com.code.service.template.api.impl;

import com.code.service.template.api.TemplateApi;
import com.code.service.template.service.TemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author 愆凡
 * @date 2022/6/12 18:38
 */
@Slf4j
@DubboService(version = "1.0")
public class TemplateApiImpl implements TemplateApi {

	@Resource
	private TemplateService templateService;

	@Override
	public void test() {

	}

}
