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

package com.code.framework.basic.result;

import com.code.framework.basic.result.code.ExceptionCode;

/**
 * @author Snow
 * @date 2023/5/20 12:35
 */
public interface ResultAccessor {

	default <T> CommonResult<T> success(T data) {
		return CommonResult.success(data);
	}

	default <T> CommonResult<T> error(ExceptionCode exceptionCode) {
		return CommonResult.error(exceptionCode);
	}

	default <T> CommonResult<T> error(ExceptionCode exceptionCode, String message) {
		return CommonResult.error(exceptionCode, message);
	}

}
