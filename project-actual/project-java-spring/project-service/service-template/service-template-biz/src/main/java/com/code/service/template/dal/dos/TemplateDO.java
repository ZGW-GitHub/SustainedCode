package com.code.service.template.dal.dos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/12 17:43
 */
@Slf4j
@Data
@Accessors(chain = true)
@TableName("template")
public class TemplateDO {

	@TableField(fill = FieldFill.INSERT)
	private Long recordNo;

	private String name;

	private Double price;

	private String coverImgUrl;

}
