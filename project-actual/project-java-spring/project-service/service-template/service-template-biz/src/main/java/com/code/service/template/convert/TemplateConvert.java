package com.code.service.template.convert;

import com.code.service.template.controller.vo.TemplateCreateReqVO;
import com.code.service.template.dal.dos.TemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 愆凡
 * @date 2022/6/12 21:39
 */
@Mapper
public interface TemplateConvert {

	TemplateConvert INSTANCE = Mappers.getMapper(TemplateConvert.class);

	TemplateDO voToDo(TemplateCreateReqVO templateCreateReqVO);

}
