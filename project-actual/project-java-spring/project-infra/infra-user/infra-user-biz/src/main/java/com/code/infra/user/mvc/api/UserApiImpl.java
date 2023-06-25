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

package com.code.infra.user.mvc.api;

import com.code.framework.basic.util.InvokeUtil;
import com.code.infra.user.mvc.api.domain.LoginReq;
import com.code.infra.user.mvc.api.domain.LoginResp;
import com.code.infra.user.mvc.api.domain.RegisterReq;
import com.code.infra.user.mvc.api.domain.RegisterResp;
import com.code.infra.user.mvc.biz.UserBiz;
import com.code.infra.user.mvc.biz.domain.LoginBO;
import com.code.infra.user.mvc.biz.domain.RegisterBO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Snow
 * @date 2023/6/25 17:10
 */
@Slf4j
@Component
public class UserApiImpl implements UserApi {

	@Resource
	private UserBiz userBiz;

	/**
	 * 登录
	 *
	 * @param loginReq 登录请求
	 * @return {@link LoginResp}
	 */
	@Override
	public LoginResp login(LoginReq loginReq) {
		return InvokeUtil.invoke(loginReq, LoginResp::new, userBiz::login, LoginBO::new);
	}

	/**
	 * 注册
	 *
	 * @param registerReq 注册申请
	 * @return {@link RegisterResp}
	 */
	@Override
	public RegisterResp register(RegisterReq registerReq) {
		return InvokeUtil.invoke(registerReq, RegisterResp::new, userBiz::register, RegisterBO::new);
	}

}
