package com.code.service.template.controller;

import cn.hutool.json.JSONUtil;
import com.code.framework.web.controller.GatewayController;
import com.code.service.template.mvc.api.domain.TemplateSaveReq;
import com.code.service.template.mvc.service.TemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

/**
 * @author 愆凡
 * @date 2022/6/27 18:20
 */
@Slf4j
@WebMvcTest(controllers = GatewayController.class)
class TemplateControllerTest {

	@Resource
	private MockMvc mockMvc;

	@MockBean
	private TemplateService templateService;

	@Test
	void save() throws Exception {
		TemplateSaveReq reqVO = new TemplateSaveReq();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.post("/goodsInfo/save")
						.characterEncoding(Charset.defaultCharset())
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSONUtil.toJsonStr(reqVO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		System.err.println(result.getResponse().getContentAsString(Charset.defaultCharset()));
	}

}
