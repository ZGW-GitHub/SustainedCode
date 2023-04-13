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

package com.code.spring.mybatis.controller;

import com.code.spring.mybatis.service.TransactionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TransactionController {

    @Resource
    private TransactionService transactionService;

    @PostMapping("blockUpdate")
    public String blockUpdate() {
        return transactionService.blockUpdate();
    }

    @PostMapping("lockSelect")
    public String lockSelect() {
        return transactionService.lockSelect();
    }

    @PostMapping("transactionSelect")
    public String transactionSelect() {
        return transactionService.transactionSelect();
    }

    @PostMapping("unTransactionSelect")
    public String unTransactionSelect() {
        return transactionService.unTransactionSelect();
    }

}
