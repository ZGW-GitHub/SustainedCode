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

package com.code.framework.web.controller;

import com.code.framework.web.controller.domain.GatewayRequest;
import com.code.framework.web.controller.domain.GatewayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2023/5/20 20:00
 */
@Slf4j
@RestController
public class GatewayController {

	@PostMapping("gateway")
	public GatewayResponse<?> gateway(@RequestBody GatewayRequest gatewayRequest) {
		System.err.println("gateway ...");

		return null;
	}

}
