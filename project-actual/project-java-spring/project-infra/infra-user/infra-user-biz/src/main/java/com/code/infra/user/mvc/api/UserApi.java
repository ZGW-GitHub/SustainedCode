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

import com.code.framework.web.api.annotation.Api;
import com.code.infra.user.mvc.api.domain.LoginReq;
import com.code.infra.user.mvc.api.domain.LoginResp;
import com.code.infra.user.mvc.api.domain.RegisterReq;
import com.code.infra.user.mvc.api.domain.RegisterResp;
import org.springframework.validation.annotation.Validated;

/**
 * @author Snow
 * @date 2023/6/25 17:09
 */
@Validated
public interface UserApi {

	/**
	 * 登录
	 *
	 * @param loginReq 登录请求
	 * @return {@link LoginResp}
	 */
	@Api("user.login")
	LoginResp login(LoginReq loginReq);

	/**
	 * 注册
	 *
	 * @param registerReq 注册申请
	 * @return {@link RegisterResp}
	 */
	@Api("user.register")
	RegisterResp register(RegisterReq registerReq);

}
