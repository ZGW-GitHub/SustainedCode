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

package com.code.service.template.mvc.dal.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.framework.mybatis.mapper.BaseMapper;
import com.code.service.template.mvc.dal.domain.dos.TemplateDO;
import com.code.service.template.mvc.service.domain.TemplatePageBO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 愆凡
 * @date 2022/6/12 18:41
 */
@Mapper
public interface TemplateMapper extends BaseMapper<TemplateDO> {

	default List<TemplateDO> list() {
		return chainQueryWrapper().list();
	}

	default Page<TemplateDO> page(TemplatePageBO pageBO) {
		return chainQueryWrapper().page(new Page<>(pageBO.currentPage(), pageBO.pageSize()));
	}

}
