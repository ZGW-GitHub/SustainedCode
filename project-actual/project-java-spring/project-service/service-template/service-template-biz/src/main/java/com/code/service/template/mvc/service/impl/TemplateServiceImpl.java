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

package com.code.service.template.mvc.service.impl;

import com.code.framework.basic.domain.page.PageResp;
import com.code.framework.basic.util.BeanUtil;
import com.code.framework.basic.util.IdGenerator;
import com.code.framework.basic.util.InvokeUtil;
import com.code.service.template.mvc.dal.domain.dos.TemplateDO;
import com.code.service.template.mvc.dal.domain.query.TemplatePageQuery;
import com.code.service.template.mvc.dal.mapper.TemplateMapper;
import com.code.service.template.mvc.service.TemplateService;
import com.code.service.template.mvc.service.domain.TemplateCreateBO;
import com.code.service.template.mvc.service.domain.TemplateDetailDTO;
import com.code.service.template.mvc.service.domain.TemplatePageBO;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author 愆凡
 * @date 2022/6/12 18:34
 */
@Slf4j
@Setter
@Service
public class TemplateServiceImpl implements TemplateService {

	private ApplicationContext applicationContext;

	@Resource
	private TemplateMapper templateMapper;

	@Override
	public String save(TemplateCreateBO createBO) {
		TemplateDO templateDO = BeanUtil.map(createBO, TemplateDO::new);
		templateDO.setRecordNo(IdGenerator.recordNo());
		templateMapper.insert(templateDO);

		// applicationContext.publishEvent(new RocketSendEvent(new TestMessage()));

		return templateDO.getRecordNo();
	}

	@Override
	public PageResp<TemplateDetailDTO> page(TemplatePageBO pageBO) {
		return InvokeUtil.invokePage(pageBO, TemplateDetailDTO::new, templateMapper::page, TemplatePageQuery::new);
	}

}
