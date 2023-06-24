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

package com.code.infra.user.mvc.biz;

import com.code.infra.user.mvc.biz.domain.LoginBO;
import com.code.infra.user.mvc.biz.domain.LoginDTO;

/**
 * @author Snow
 * @date 2023/6/24 22:23
 */
public interface LoginBiz {

	/**
	 * 登录
	 *
	 * @param loginBO 登录请求
	 *
	 * @return {@link LoginDTO}
	 */
	LoginDTO login(LoginBO loginBO);

}
