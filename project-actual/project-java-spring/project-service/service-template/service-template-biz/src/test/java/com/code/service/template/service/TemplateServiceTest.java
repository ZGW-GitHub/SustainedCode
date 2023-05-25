package com.code.service.template.service;

import com.code.service.template.ServiceTemplateApplicationTest;
import com.code.service.template.mvc.service.TemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author 愆凡
 * @date 2022/6/13 23:16
 */
@Slf4j
class TemplateServiceTest extends ServiceTemplateApplicationTest {

	@Resource
	private TemplateService templateService;

	@Test
	void saveTest() {
	}

}
