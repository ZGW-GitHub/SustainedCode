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

package com.code.framework.basic.web.api.exception;

import com.code.framework.basic.exception.BizException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2023/5/21 15:02
 */
@Slf4j
public class ApiException extends BizException {

	public ApiException(ApiExceptionCode resultCode) {
		super(resultCode);
	}

	public ApiException(ApiExceptionCode resultCode, String message) {
		super(resultCode, message);
	}

}
