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

package com.code.framework.basic.exception.core;

import java.util.function.Supplier;

/**
 * Code 规范：<br/>
 * <ul>
 *     <li>均为 6 位数字</li>
 *     <li>BizExceptionCode 	: 11xxxx</li>
 *     <li>ApiExceptionCode 	: 12xxxx</li>
 * </ul>
 *
 * @author Snow
 * @date 2023/5/21 15:12
 */
public interface ExceptionCode<E extends Exception> {

	int getCode();

	String getMessage();

	Supplier<E> getSupplier();

	default E exception() {
		return exception(getMessage());
	}

	default E exception(String msgFormat, Object... args) {
		String msg = (args != null && args.length > 0) ? String.format(msgFormat, args) : msgFormat;

		E exception = getSupplier().get();
		exception.setCode(getCode());
		exception.setMessage(msg);
		return exception;
	}

}
