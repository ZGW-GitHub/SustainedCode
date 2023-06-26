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

package com.code.service.template.mvc.api;

import com.code.framework.basic.domain.page.PageResp;
import com.code.framework.web.api.annotation.Api;
import com.code.service.template.mvc.api.domain.TemplateDetailResp;
import com.code.service.template.mvc.api.domain.TemplatePageReq;
import com.code.service.template.mvc.api.domain.TemplateSaveReq;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * @author Snow
 * @date 2023/5/20 19:34
 */
@Validated
public interface TemplateApi {

	@Api("template.test")
	String test();

	@Api("template.save")
	String save(@Valid TemplateSaveReq templateSaveReq);

	@Api("template.page")
	PageResp<TemplateDetailResp> page(TemplatePageReq templatePageReq);

}
