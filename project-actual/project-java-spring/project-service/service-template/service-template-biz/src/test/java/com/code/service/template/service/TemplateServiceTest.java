package com.code.service.template.service;

import com.code.service.template.ServiceTemplateApplicationTest;
import com.code.service.template.controller.vo.GoodsInfoCreateReqVO;
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
		Long recordId = templateService.save(GoodsInfoCreateReqVO.builder().name("test").price(1.1).coverImgUrl("test").build());

		System.err.println(recordId);
	}

}
