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

package com.code.service.template.mvc.api.impl;

import com.code.framework.basic.domain.page.PageResp;
import com.code.framework.basic.util.InvokeUtil;
import com.code.service.template.mvc.api.TemplateApi;
import com.code.service.template.mvc.api.domain.TemplateDetailResp;
import com.code.service.template.mvc.api.domain.TemplatePageReq;
import com.code.service.template.mvc.api.domain.TemplateSaveReq;
import com.code.service.template.mvc.service.TemplateService;
import com.code.service.template.mvc.service.domain.TemplateCreateBO;
import com.code.service.template.mvc.service.domain.TemplatePageBO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Snow
 * @date 2023/5/20 19:36
 */
@Slf4j
@Component
public class TemplateApiImpl implements TemplateApi {

	@Resource
	private TemplateService templateService;

	@Override
	public String test() {
		return "test";
	}

	@Override
	public String save(TemplateSaveReq createReq) {
		return InvokeUtil.invoke(createReq, templateService::save, TemplateCreateBO::new);
	}

	@Override
	public PageResp<TemplateDetailResp> page(TemplatePageReq templatePageReq) {
		return InvokeUtil.invokePage(templatePageReq, TemplateDetailResp::new, templateService::page, TemplatePageBO::new);
	}

}
