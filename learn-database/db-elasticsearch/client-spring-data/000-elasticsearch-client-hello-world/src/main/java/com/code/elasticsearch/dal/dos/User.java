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

package com.code.elasticsearch.dal.dos;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author Snow
 * @date 2022/6/14 16:33
 */
@Slf4j
@Data
@Accessors(fluent = true)
@Document(indexName = "user")
public class User {

	@Id
	@Field(type = FieldType.Integer, index = false)
	private Integer id;

	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String name;

	@Field(type = FieldType.Integer)
	private Integer age;

	@Field(type = FieldType.Date)
	private Date createTime;

	@Field(type = FieldType.Object, enabled = false)
	private Date updateTime;

}
