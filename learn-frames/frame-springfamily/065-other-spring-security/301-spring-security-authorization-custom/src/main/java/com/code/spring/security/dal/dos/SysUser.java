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

package com.code.spring.security.dal.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Snow
 * @date 2020/8/14 11:49 上午
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

	@Serial
	private static final long serialVersionUID = -5625175115874392926L;

	private Integer id;
	private Long    recordId;
	private String  account;
	private String  password;
	private String  username;
	private Integer age;
	private Date    createTime;
	private Date    updateTime;

}
