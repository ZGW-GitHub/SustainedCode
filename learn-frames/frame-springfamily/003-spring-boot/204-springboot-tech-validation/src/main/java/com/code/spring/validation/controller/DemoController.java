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

package com.code.spring.validation.controller;

import com.code.spring.validation.controller.vo.DemoReqVO;
import com.code.spring.validation.service.DemoService;
import com.code.spring.validation.service.dto.DemoReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Snow
 * @date 2022/7/31 16:01
 */
@Slf4j
@RestController
public class DemoController {

	@Resource
	private DemoService demoService;

	@PostMapping("demo")
	public String demo(@RequestBody @Valid @NotNull DemoReqVO reqVO) {
		demoService.demo(new DemoReqDTO().setAge(reqVO.getAge()).setName(reqVO.getName()));
		// demoService.demo(null);

		return "SUCCESS";
	}

}
