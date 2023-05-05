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

package com.code.spring.boot.component.transaction;

import com.code.spring.boot.component.datasource.DataSourceEnum;
import org.springframework.transaction.annotation.Isolation;

import java.lang.annotation.*;

/**
 * @author Snow
 * @date 2023/5/5 22:09
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiTransaction {

	String transactionManager() default "multiTransactionManager";

	/**
	 * 隔离级别，默认为数据库本身默认值
	 *
	 * @return {@link Isolation}
	 */
	Isolation isolationLevel() default Isolation.DEFAULT;

	/**
	 * 默认主数据源id
	 *
	 * @return {@link String}
	 */
	DataSourceEnum datasourceId() default DataSourceEnum.DEFAULT;

	/**
	 * 是否为只读事务
	 *
	 * @return boolean
	 */
	boolean readOnly() default false;

}
