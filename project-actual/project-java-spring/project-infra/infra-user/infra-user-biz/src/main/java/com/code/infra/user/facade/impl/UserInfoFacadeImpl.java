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

package com.code.infra.user.facade.impl;

import com.code.framework.basic.util.InvokeUtil;
import com.code.infra.user.facade.UserInfoFacade;
import com.code.infra.user.facade.domain.UserInfoDetailResp;
import com.code.infra.user.facade.domain.UserInfoQueryReq;
import com.code.infra.user.mvc.service.UserInfoService;
import com.code.infra.user.mvc.service.domain.UserInfoDetailBO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Snow
 * @date 2023/6/26 10:15
 */
@Slf4j
@DubboService
public class UserInfoFacadeImpl implements UserInfoFacade {

	@Resource
	private UserInfoService userInfoService;

	@Override
	public UserInfoDetailResp userInfo(UserInfoQueryReq userInfoQueryReq) {
		return InvokeUtil.invoke(userInfoQueryReq, UserInfoDetailResp::new, userInfoService::findUserInfo, UserInfoDetailBO::new);
	}

}
