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

package com.code.infra.user.mvc.service.impl;

import com.code.infra.user.convert.UserInfoConvert;
import com.code.infra.user.exception.UserExceptionCode;
import com.code.infra.user.mvc.dal.domain.dos.UserInfoDO;
import com.code.infra.user.mvc.dal.mapper.UserInfoMapper;
import com.code.infra.user.mvc.service.UserInfoService;
import com.code.infra.user.mvc.service.model.UserInfoDetailReqModel;
import com.code.infra.user.mvc.service.model.UserInfoDetailRespModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Snow
 * @date 2023/6/21 15:22
 */
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private UserInfoMapper userInfoMapper;

	/**
	 * 查找用户信息
	 *
	 * @param reqModel req
	 * @return {@link UserInfoDetailRespModel}
	 */
	@Override
	public UserInfoDetailRespModel findUserInfo(UserInfoDetailReqModel reqModel) {
		Optional<UserInfoDO> userInfoDO = userInfoMapper.findByAccount(reqModel.getAccount());

		return userInfoDO.map(UserInfoConvert.INSTANCE::doToModel).orElseThrow(UserExceptionCode.USER_NOT_EXIST::exception);
	}

}
