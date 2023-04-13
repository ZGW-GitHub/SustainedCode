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

package com.code.spring.custom.config.label.schema.component;

import com.code.spring.custom.config.label.schema.AppConfig;
import com.code.spring.custom.config.label.schema.ConsumerConfig;
import com.code.spring.custom.config.label.schema.ProviderConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Snow
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		super.registerBeanDefinitionParser("app", new MyBeanDefinitionParser(AppConfig.class));
		super.registerBeanDefinitionParser("provider", new MyBeanDefinitionParser(ProviderConfig.class));
		super.registerBeanDefinitionParser("consumer", new MyBeanDefinitionParser(ConsumerConfig.class));
	}

}
