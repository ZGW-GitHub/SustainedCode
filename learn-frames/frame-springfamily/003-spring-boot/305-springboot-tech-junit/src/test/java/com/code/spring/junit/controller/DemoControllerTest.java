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

package com.code.spring.junit.controller;

import static org.junit.jupiter.api.Assertions.*;

import cn.hutool.json.JSONUtil;
import com.code.spring.junit.service.DemoService;
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

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * @author Snow
 * @date 2022/7/26 15:23
 */
@Slf4j
@WebMvcTest(DemoController.class)
class DemoControllerTest {

	@Resource
	private MockMvc mockMvc;

	@MockBean
	private DemoService demoService;

	@Test
	void demo() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
												   .post("/demo")
												   .characterEncoding(Charset.defaultCharset())
												   .contentType(MediaType.APPLICATION_JSON)
												   .content(JSONUtil.toJsonStr(new Object())))
								  .andExpect(MockMvcResultMatchers.status().isOk())
								  .andDo(MockMvcResultHandlers.print())
								  .andReturn();

		System.err.println(result.getResponse().getContentAsString(Charset.defaultCharset()));
	}

}
