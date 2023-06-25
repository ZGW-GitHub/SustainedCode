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

package com.code.infra.user.mvc.biz.impl;

import com.code.framework.basic.util.BeanUtil;
import com.code.framework.basic.util.PasswordUtil;
import com.code.infra.user.framework.exception.UserExceptionCode;
import com.code.infra.user.mvc.biz.UserBiz;
import com.code.infra.user.mvc.biz.domain.LoginBO;
import com.code.infra.user.mvc.biz.domain.LoginDTO;
import com.code.infra.user.mvc.biz.domain.RegisterBO;
import com.code.infra.user.mvc.biz.domain.RegisterDTO;
import com.code.infra.user.mvc.dal.domain.dos.UserInfoDO;
import com.code.infra.user.mvc.dal.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Snow
 * @date 2023/6/24 22:23
 */
@Slf4j
@Service
public class UserBizImpl implements UserBiz {

	@Resource
	private UserInfoMapper userInfoMapper;

	/**
	 * 登录
	 *
	 * @param loginBO 登录请求
	 *
	 * @return {@link LoginDTO}
	 */
	@Override
	public LoginDTO login(LoginBO loginBO) {
		Optional<UserInfoDO> userInfoDOOpt = userInfoMapper.findByAccount(loginBO.getAccount());
		if (userInfoDOOpt.isEmpty()) {
			throw UserExceptionCode.USER_ACCOUNT_OR_PASSWORD_INCORRECT.exception();
		}

		UserInfoDO userInfoDO = userInfoDOOpt.get();
		if (!PasswordUtil.check(loginBO.getPassword(), userInfoDO.getSalt(), userInfoDO.getPassword())) {
			throw UserExceptionCode.USER_ACCOUNT_OR_PASSWORD_INCORRECT.exception();
		}

		// 用户名密码正确
		// TODO

		return BeanUtil.map(userInfoDO, LoginDTO::new);
	}

	/**
	 * 注册
	 *
	 * @param registerBO registerBO
	 * @return {@link RegisterDTO}
	 */
	@Override
	public RegisterDTO register(RegisterBO registerBO) {
		Optional<UserInfoDO> userInfoDOOpt = userInfoMapper.findByAccount(registerBO.getAccount());
		if (userInfoDOOpt.isPresent()) {
			throw UserExceptionCode.USER_EXIST.exception();
		}

		UserInfoDO userInfoDO = BeanUtil.map(registerBO, UserInfoDO::new);
		String salt = PasswordUtil.generateSalt();
		userInfoDO.setSalt(salt);
		userInfoDO.setPassword(PasswordUtil.encode(registerBO.getPassword(), salt));
		userInfoMapper.insert(userInfoDO);

		return new RegisterDTO();
	}

}
