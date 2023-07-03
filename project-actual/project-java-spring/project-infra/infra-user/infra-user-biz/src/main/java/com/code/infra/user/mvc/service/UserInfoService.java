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

package com.code.infra.user.mvc.service;

import com.code.infra.user.mvc.service.domain.*;

/**
 * @author Snow
 * @date 2023/6/21 15:20
 */
public interface UserInfoService {

	/**
	 * 查找用户信息
	 *
	 * @param userInfoDetailBO userInfoDetailBO
	 * @return {@link UserInfoDetailDTO}
	 */
	UserInfoDetailDTO findUserInfo(UserInfoDetailBO userInfoDetailBO);

	/**
	 * 查询认证所需信息
	 *
	 * @param userAuthBO 查询入参
	 *
	 * @return {@link UserAuthDTO}
	 */
	UserAuthDTO findAuthInfo(UserAuthBO userAuthBO);

	/**
	 * 查询需要存储到 token 的信息
	 *
	 * @param tokenInfoBO 查询入参
	 *
	 * @return {@link TokenInfoDTO}
	 */
	TokenInfoDTO findTokenInfo(TokenInfoBO tokenInfoBO);

}
