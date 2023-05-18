package com.code.service.template.service;

import com.code.service.template.controller.vo.TemplateCreateReqVO;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 愆凡
 * @date 2022/6/12 18:15
 */
public interface TemplateService extends ApplicationContextAware {

	Long save(TemplateCreateReqVO reqVO);

}
