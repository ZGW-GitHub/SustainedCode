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

package com.code.spring.custom.scope.component.annotation;

import com.code.spring.custom.scope.component.RefreshScope;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

/**
 * @author Snow
 * @date 2022/3/20 20:22
 */
@Target({ElementType.TYPE, ElementType.METHOD})            // 拷贝于 @Scope
@Retention(RetentionPolicy.RUNTIME)                        // 拷贝于 @Scope
@Documented                                                // 拷贝于 @Scope
@Scope(RefreshScope.SCOPE_REFRESH)
public @interface RefreshScopeAnno {

	/**
	 * @see Scope#proxyMode()
	 */
	ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;
	// ScopedProxyMode proxyMode() default ScopedProxyMode.DEFAULT;

}
