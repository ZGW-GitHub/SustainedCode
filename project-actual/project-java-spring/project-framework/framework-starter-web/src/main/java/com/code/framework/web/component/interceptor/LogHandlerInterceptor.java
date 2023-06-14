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

package com.code.framework.web.component.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Snow
 * @date 2023/6/14 11:04
 */
@Slf4j
@Component
public class LogHandlerInterceptor implements HandlerInterceptor {

	/**
	 * 处理请求前执行
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param handler  处理程序
	 * @return boolean
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.debug("处理请求：{}, 入参：{}", "todo", "todo");

		return true;
	}

	/**
	 * 处理请求后执行
	 *
	 * @param request      请求
	 * @param response     响应
	 * @param handler      处理程序
	 * @param modelAndView 模型和视图
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

	}

	/**
	 * 渲染视图后执行
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param handler  处理程序
	 * @param ex       异常
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

	}

}
