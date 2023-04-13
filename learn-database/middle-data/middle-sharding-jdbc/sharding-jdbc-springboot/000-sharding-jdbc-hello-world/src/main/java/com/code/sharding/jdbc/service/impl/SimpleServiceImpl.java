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

package com.code.sharding.jdbc.service.impl;

import cn.hutool.core.util.IdUtil;
import com.code.sharding.jdbc.dal.simple.dos.SimpleUser;
import com.code.sharding.jdbc.dal.simple.mapper.SimpleUserMapper;
import com.code.sharding.jdbc.service.SimpleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Snow
 * @date 2022/11/5 16:48
 */
@Slf4j
@Service
public class SimpleServiceImpl implements SimpleService {

    @Resource
    private SimpleUserMapper simpleUserMapper;

    @Override
    @Transactional(transactionManager = "simpleDsTransactionManager")
    public void demo() {
        for (int i = 0; i < 10; i++) {
            SimpleUser user = new SimpleUser().setName("test" + i).setAge(i).setRecordId(IdUtil.getSnowflakeNextId() + i);

            log.debug("普通 >>> 保存 user ：{}", user);

            simpleUserMapper.save(user);
        }
    }

}
