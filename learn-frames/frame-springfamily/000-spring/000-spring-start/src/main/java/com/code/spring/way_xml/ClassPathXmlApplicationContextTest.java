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

package com.code.spring.way_xml;

import com.code.spring.UseApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Snow
 * @date 2022/4/24 21:27
 */
@Slf4j
public class ClassPathXmlApplicationContextTest {
	public static void main(String[] args) {

		// 1、创建基于 xml 的 Spring 应用上下文
		AbstractXmlApplicationContext context = new ClassPathXmlApplicationContext();
		// 2、设置 xml 配置文件所在位置
		context.setConfigLocations("beans.xml");
		// 3、刷新 Spring 应用上下文
		context.refresh();

		// 可选：发布 Spring 上下文启动完成事件
		context.start();

		// 使用
		UseApplicationContextUtil.useApplicationContext(context);

	}
}
