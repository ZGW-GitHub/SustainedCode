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

package com.code.framework.mybatis.component;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.fill.Column;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Snow
 * @date 2023/6/20 18:24
 */
@Slf4j
@Component
public class MybatisPlusGenerator {

	public void generator() {
		FastAutoGenerator.create("DATA_SOURCE_CONFIG", "", "")
				// 全局配置
				// .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")))
				.globalConfig(builder -> builder.author("snow"))
				// 包配置
				.packageConfig(builder -> builder
						.parent("com.code.service.template")
						.entity("mvc.dal.domain.dos")
						.mapper("mvc.dal.mapper")
						.xml("mvc.dal.mapper")
						.service("mvc.service")
						.serviceImpl("mvc.service.impl")
				)
				// 策略配置
				.strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
						.controllerBuilder().enableRestStyle().enableHyphenStyle()
						.entityBuilder().enableLombok().addTableFills(
								new Column("create_time", FieldFill.INSERT)
						).build())
				.execute();
	}

	// 处理 all 情况
	protected static List<String> getTables(String tables) {
		return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
	}

}
