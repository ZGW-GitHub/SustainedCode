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

package com.code.infra.user.mvc.dal.mapper;

import com.code.framework.mybatis.mapper.BaseMapper;
import com.code.infra.user.mvc.dal.domain.dos.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * @author Snow
 * @date 2023/6/21 15:20
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoDO> {

	default Optional<UserInfoDO> findByAccount(String account) {
		return lambdaChainQueryWrapper()
				.eq(UserInfoDO::getAccount, account)
				.last(" limit 1 ")
				.oneOpt();
	}

}
