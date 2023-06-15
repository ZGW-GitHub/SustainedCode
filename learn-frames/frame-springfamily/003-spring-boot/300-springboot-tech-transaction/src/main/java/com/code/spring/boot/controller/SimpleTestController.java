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

package com.code.spring.boot.controller;

import com.code.spring.boot.service.SimpleTestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2022/5/5 21:32
 */
@Slf4j
@RestController
@RequestMapping("simple")
public class SimpleTestController {

	@Resource
	private SimpleTestService simpleTestService;

	@PostMapping("test1")
	public String test1() {
		simpleTestService.test1();

		return "SUCCESS";
	}

	@PostMapping("test2")
	public String test2() {
		simpleTestService.test2();

		return "SUCCESS";
	}

}
