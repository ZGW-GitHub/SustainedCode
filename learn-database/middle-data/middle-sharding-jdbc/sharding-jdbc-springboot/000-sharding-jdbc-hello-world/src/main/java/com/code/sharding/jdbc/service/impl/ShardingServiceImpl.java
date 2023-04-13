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

import cn.hutool.core.util.RandomUtil;
import com.code.sharding.jdbc.dal.sharding.dos.ShardingUser;
import com.code.sharding.jdbc.dal.sharding.mapper.ShardingUserMapper;
import com.code.sharding.jdbc.service.ShardingService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Snow
 * @date 2022/11/5 16:50
 */
@Slf4j
@Service
public class ShardingServiceImpl implements ShardingService {

	@Resource
	private ShardingUserMapper shardingUserMapper;

	@Override
	@Transactional(transactionManager = "shardingDsTransactionManager")
	public void demo() {
		for (int i = 1; i <= 100; i++) {
			ShardingUser user = new ShardingUser().setName("test" + i).setAge(i).setRecordId(RandomUtil.randomLong(10) + i * 100);

			log.debug("分库分表 >>> 保存 user ：{}", user);
			if (i == 5) {
				// log.info("触发异常：{}", 1/0);
			}

			shardingUserMapper.save(user);
		}
	}

	/**
	 * 根据 recordId 查询列表
	 *
	 * @param recordId 记录id
	 * @return {@link List}<{@link ShardingUser}>
	 */
	@Override
	public List<ShardingUser> listByRecordId(Long recordId) {
		return shardingUserMapper.listByRecordId(recordId);
	}
}
