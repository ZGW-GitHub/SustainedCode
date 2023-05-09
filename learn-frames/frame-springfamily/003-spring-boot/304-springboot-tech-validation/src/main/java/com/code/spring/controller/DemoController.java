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

package com.code.spring.controller;

import com.code.spring.controller.vo.DemoReqVO;
import com.code.spring.service.DemoService;
import com.code.spring.service.dto.DemoReqDTO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2022/7/31 16:01
 */
@Slf4j
@RestController
@RequestMapping("test")
public class DemoController {

	@Resource
	private DemoService demoService;

	@PostMapping("controller")
	public String demo(@RequestBody @NotNull @Valid DemoReqVO reqVO) {
		log.debug("收到请求，参数：{}", reqVO);

		return "SUCCESS";
	}

	@PostMapping("service")
	public String service(@RequestBody DemoReqVO reqVO) {
		log.debug("收到请求，参数：{}", reqVO);

		demoService.demo(new DemoReqDTO().setAge(reqVO.getAge()).setName(reqVO.getName()));

		return "SUCCESS";
	}

}
