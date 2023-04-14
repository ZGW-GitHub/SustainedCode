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

package com.code.sharding.jdbc.service;

import com.code.sharding.jdbc.dal.sharding.dos.ShardingUser;

import java.util.List;

/**
 * @author Snow
 * @date 2022/11/5 16:50
 */
public interface ShardingService {

    void demo();

    /**
     * 根据 recordId 查询列表
     *
     * @param recordId 记录id
     * @return {@link List}<{@link ShardingUser}>
     */
    List<ShardingUser> listByRecordId(Long recordId);

}
