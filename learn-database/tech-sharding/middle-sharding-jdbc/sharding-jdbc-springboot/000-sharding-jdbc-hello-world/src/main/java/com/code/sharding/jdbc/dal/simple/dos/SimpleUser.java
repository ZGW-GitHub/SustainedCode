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

package com.code.sharding.jdbc.dal.simple.dos;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Snow
 * @date 2023/4/6 12:59
 */
@Slf4j
@Data
@Table(name = "user")
@Accessors(chain = true)
public class SimpleUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 4016705434321385320L;

	private Integer id;
	private Long recordId;
	private String name;
	private Integer age;
	private Date createTime;
	private Date updateTime;
}
