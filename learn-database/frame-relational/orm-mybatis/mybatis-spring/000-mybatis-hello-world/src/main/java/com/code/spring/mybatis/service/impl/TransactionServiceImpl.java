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

package com.code.spring.mybatis.service.impl;

import com.code.spring.mybatis.dal.dos.User;
import com.code.spring.mybatis.dal.mapper.UserMapper;
import com.code.spring.mybatis.service.TransactionService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private UserMapper userMapper;

	@Resource(name = "snowDsTransactionManager")
    private DataSourceTransactionManager transactionManager;

    private final Integer userid = 816;
    private final String name = "demo2";

    @Override
    public String blockUpdate() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userMapper.updateName(name, userid);

            log.info("start sleep ...");
            TimeUnit.SECONDS.sleep(10);

            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("发生异常：", e);
            transactionManager.rollback(status);
        }

        return "SUCCESS";
    }

    @Override
    public String lockSelect() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 证明了 排它锁 不会阻塞普通的 Select 操作
            User user = userMapper.selectByLock(userid);

            log.info("start sleep , User : " + user);
            TimeUnit.SECONDS.sleep(10);

            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("发生异常：", e);
            transactionManager.rollback(status);
        }

        return "SUCCESS";
    }

    @Override
    public String transactionSelect() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            for (int i = 0; i < 15; i++) {
                // 从头到尾使用的都是一个事务
                List<User> userList = userMapper.findById(userid);

                userList.forEach(System.err::println);

                TimeUnit.SECONDS.sleep(1);
            }

            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("发生异常：", e);
            transactionManager.rollback(status);
        }

        return "SUCCESS";
    }

    @Override
    @SneakyThrows
    public String unTransactionSelect() {
        for (int i = 0; i < 15; i++) {
            // 每次查询使用新的事物
            List<User> userList = userMapper.findById(userid);

            userList.forEach(System.out::println);

            TimeUnit.SECONDS.sleep(1);
        }

        return "SUCCESS";
    }

}
