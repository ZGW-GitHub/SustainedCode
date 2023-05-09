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

package com.code.nacos.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.code.nacos.entity.DynamicConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/4/28 11:51
 */
@Slf4j
@RestController
public class DemoController {

	@Resource
	private DynamicConfig dynamicConfig;

	@NacosInjected
	private ConfigService configService;

	@PostMapping("demo")
	public String demo() {
		return dynamicConfig.getName();
	}

	@PostMapping("update")
	@SneakyThrows
	public String update() {
		String config = configService.getConfig("TestConfig", "DEFAULT_GROUP", TimeUnit.SECONDS.toMillis(3));
		System.err.println(config);

		DynamicConfig newConfig = new DynamicConfig().setName("update-" + DateUtil.now());
		configService.publishConfig("TestConfig", "DEFAULT_GROUP", JSONUtil.toJsonStr(newConfig), ConfigType.JSON.getType());

		return "SUCCESS";
	}

}
