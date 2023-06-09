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

package com.code.service.template.mvc.service;

import com.code.framework.basic.domain.page.PageResp;
import com.code.service.template.mvc.service.domain.TemplateDetailDTO;
import com.code.service.template.mvc.service.domain.TemplatePageBO;
import com.code.service.template.mvc.service.domain.TemplateSaveBO;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 愆凡
 * @date 2022/6/12 18:15
 */
public interface TemplateService extends ApplicationContextAware {

	String save(TemplateSaveBO createReqModel);

	PageResp<TemplateDetailDTO> page(TemplatePageBO templatePageBO);

}
