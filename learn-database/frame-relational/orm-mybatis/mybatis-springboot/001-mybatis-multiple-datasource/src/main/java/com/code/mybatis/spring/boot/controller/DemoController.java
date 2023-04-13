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

package com.code.mybatis.spring.boot.controller;

import com.code.mybatis.spring.boot.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2022/10/17 10:01
 */
@Slf4j
@RestController
public class DemoController {

    @Resource
    private UserService userService;

    @PostMapping("demo")
    public String demo() {
        userService.demo();

        return "SUCCESS";
    }

    @PostMapping("transaction/code")
    public String transactionByCode() {
        userService.transactionByCode();

        return "SUCCESS";
    }

    @PostMapping("transaction/anno")
    public String transactionByAnno() {
        userService.transactionByAnno();

        return "SUCCESS";
    }

}
