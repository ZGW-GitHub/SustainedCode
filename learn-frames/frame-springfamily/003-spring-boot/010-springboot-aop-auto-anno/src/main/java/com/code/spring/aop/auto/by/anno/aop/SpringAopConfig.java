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

package com.code.spring.aop.auto.by.anno.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * {@link EnableAspectJAutoProxy#exposeProxy()} 需要设置为 true ，否则使用 {@link AopContext#currentProxy()} 会报错
 *
 * @author Snow
 * @date 2022/6/30 10:33
 */
@Slf4j
@Configuration
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class SpringAopConfig {
}
