package com.code.service.template.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/12 18:27
 */
@Slf4j
@Data
@Accessors(chain = true)
public class TemplateCreateReqVO {

	private Long recordNo;

	private String name;

	private Double price;

	private String coverImgUrl;

}
