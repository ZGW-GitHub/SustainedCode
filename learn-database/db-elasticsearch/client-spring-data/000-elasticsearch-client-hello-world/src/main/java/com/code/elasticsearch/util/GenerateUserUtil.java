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

package com.code.elasticsearch.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.code.elasticsearch.dal.dos.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2023/4/2 19:39
 */
@Slf4j
public class GenerateUserUtil {

	public static User generate() {
		int userId = RandomUtil.randomInt(999999);
		int userAge = RandomUtil.randomInt(10, 23);
		DateTime dateTime = RandomUtil.randomDay(-365, -1);

		return new User().id(userId).name("测试账号" + userId).age(userAge).createTime(dateTime).updateTime(dateTime);
	}

}
