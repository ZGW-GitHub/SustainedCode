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

package factory_.factory.method;

import factory_.factory.method.factory.IConfigParseFactory;
import factory_.factory.method.factory.impl.JsonConfigParseFactory;
import factory_.factory.method.factory.impl.XmlConfigParseFactory;
import factory_.factory.method.factory.impl.YmlConfigParseFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 这里有相当于一个简单工厂，通过该简单工厂获取 ConfigParseFactory
 *
 * @author Snow
 * @date 2020/9/7 11:27 上午
 */
public class ConfigParseFactoryMap {

	private static final Map<String, IConfigParseFactory> PARSE_FACTORY_MAP = new HashMap<>();

	static {
		PARSE_FACTORY_MAP.put("json", new JsonConfigParseFactory());
		PARSE_FACTORY_MAP.put("xml", new XmlConfigParseFactory());
		PARSE_FACTORY_MAP.put("yml", new YmlConfigParseFactory());
	}

	/**
	 * 当 ConfigParseFactory 能复用时使用该方法获取 ConfigParseFactory
	 */
	public static IConfigParseFactory getParseFactoryByReuse(String type) {
		return PARSE_FACTORY_MAP.get(type);
	}

	/**
	 * 当 ConfigParseFactory 不能复用时使用该方法获取 ConfigParseFactory <br/>
	 * 即：每次创建一个新的 IConfigParseFactory 对象
	 */
	public static IConfigParseFactory getParseFactory(String type) {
		IConfigParseFactory parseFactory = null;

		if ("json".equalsIgnoreCase(type)) {
			parseFactory = new JsonConfigParseFactory();
		} else if ("xml".equalsIgnoreCase(type)) {
			parseFactory = new XmlConfigParseFactory();
		} else if ("yml".equalsIgnoreCase(type)) {
			parseFactory = new YmlConfigParseFactory();
		}

		return parseFactory;
	}

}
