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

import com.code.spring.boot.exception.SnowException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2023/4/5 17:04
 */
@Slf4j
@RestController
public class DemoController {

	@PostMapping("demo")
	public String demo() {
		try {
			log.info("触发异常：{}", 1 / 0);
		} catch (Exception e) {
			throw new SnowException("测试异常");
		}

		return "SUCCESS";
	}

}