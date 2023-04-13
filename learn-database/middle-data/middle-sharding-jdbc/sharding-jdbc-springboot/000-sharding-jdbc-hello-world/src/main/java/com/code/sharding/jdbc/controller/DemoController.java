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

package com.code.sharding.jdbc.controller;

import com.code.sharding.jdbc.dal.sharding.dos.ShardingUser;
import com.code.sharding.jdbc.service.ShardingService;
import com.code.sharding.jdbc.service.SimpleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Snow
 * @date 2022/11/5 16:46
 */
@Slf4j
@RestController
public class DemoController {

    @Resource
    private SimpleService simpleService;

    @Resource
    private ShardingService shardingService;

    @PostMapping("simple")
    public String simple() {
        simpleService.demo();

        return "SUCCESS";
    }

    @PostMapping("sharding")
    public String sharding() {
        shardingService.demo();

        return "SUCCESS";
    }

    @PostMapping("shardingList/{recordId}")
    public String shardingList(@PathVariable("recordId") Long recordId) {
        List<ShardingUser> shardingUserList = shardingService.listByRecordId(recordId);

        shardingUserList.forEach(System.err::println);

        return "SUCCESS";
    }

}
