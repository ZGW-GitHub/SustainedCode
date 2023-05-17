package com.code.service.template.service;

import com.code.service.template.controller.vo.GoodsInfoCreateReqVO;
import com.code.service.template.convert.TemplateConvert;
import com.code.service.template.dal.dos.TemplateDO;
import com.code.service.template.dal.mapper.TemplateMapper;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public Long save(GoodsInfoCreateReqVO reqVO) {
		TemplateDO goodsInfo = TemplateConvert.INSTANCE.convert(reqVO);
		templateMapper.insert(goodsInfo);

		// applicationContext.publishEvent(new RocketSendEvent(new TestMessage()));

		return goodsInfo.getRecordNo();
	}

}
