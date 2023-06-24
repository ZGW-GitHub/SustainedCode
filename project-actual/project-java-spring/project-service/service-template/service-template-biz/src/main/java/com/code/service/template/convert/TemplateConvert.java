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

package com.code.service.template.convert;

import com.code.service.template.mvc.api.domain.TemplateCreateReq;
import com.code.service.template.mvc.api.domain.TemplateDetailResp;
import com.code.service.template.mvc.api.domain.TemplatePageReq;
import com.code.service.template.mvc.dal.domain.dos.TemplateDO;
import com.code.service.template.mvc.dal.domain.query.TemplatePageQuery;
import com.code.service.template.mvc.service.domain.TemplateCreateBO;
import com.code.service.template.mvc.service.domain.TemplateDetailDTO;
import com.code.service.template.mvc.service.domain.TemplatePageBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 愆凡
 * @date 2022/6/12 21:39
 */
@Mapper
public interface TemplateConvert {

	TemplateConvert INSTANCE = Mappers.getMapper(TemplateConvert.class);

	TemplateCreateBO reqToBO(TemplateCreateReq createReq);

	TemplateDO boToDo(TemplateCreateBO createBO);

	TemplateDetailDTO doToDTO(TemplateDO templateDO);

	List<TemplateDetailDTO> doToDTO(List<TemplateDO> templateDOList);

	TemplatePageBO reqToBO(TemplatePageReq templatePageReq);

	TemplateDetailResp dtoToResp(TemplateDetailDTO templateDetailDTO);

	List<TemplateDetailResp> dtoToResp(List<TemplateDetailDTO> templateDetailDTOList);

	TemplatePageQuery boToQuery(TemplatePageBO pageBO);

}
