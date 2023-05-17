package com.code.service.template.dal.dos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.code.framework.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/12 17:43
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("goods_info")
public class TemplateDO extends BaseEntity {

	@TableField(fill = FieldFill.INSERT)
	private Long recordNo;

	private String name;

	private Double price;

	private String coverImgUrl;

}
