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

import com.code.service.template.convert.TemplateConvert;
import com.code.service.template.mvc.api.TemplateApi;
import com.code.service.template.mvc.api.request.TemplateCreateReqVO;
import com.code.service.template.mvc.service.TemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Snow
 * @date 2023/5/20 19:36
 */
@Slf4j
@Component
public class TemplateApiImpl implements TemplateApi {

	@Resource
	private TemplateService templateService;

	public String test() {
		return "test";
	}

	@Transactional
	public String save(TemplateCreateReqVO createReqVO) {
		return templateService.save(TemplateConvert.INSTANCE.voToModel(createReqVO));
	}

}
