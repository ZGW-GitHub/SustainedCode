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

package com.code.service.template.mvc.dal.domain.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.code.framework.mybatis.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/12 17:43
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("template")
public class TemplateDO extends BaseDO {

	private String  name;
	private Integer age;

}
