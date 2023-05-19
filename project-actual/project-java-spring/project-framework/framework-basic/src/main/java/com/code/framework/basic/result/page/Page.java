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

package com.code.framework.basic.result.page;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Snow
 * @date 2023/5/19 11:40
 */
@Setter
@Getter
public class Page implements Serializable {

	/**
	 * 当前页
	 */
	@Min(value = 1, message = "当前页不合法，应大于等于 1")
	private long currentPage = 1;

	/**
	 * 每页显示条数，默认 10
	 */
	@Min(value = 1, message = "每页显示条数不合法，应大于等于 1")
	private long pageSize = 10;

	public long currentPage() {
		return currentPage;
	}

	public long pageSize() {
		return pageSize;
	}

	public long offset() {
		long current = this.currentPage;
		if (current <= 1L) {
			return 0L;
		}
		return Math.max((current - 1) * this.pageSize, 0L);
	}

}
