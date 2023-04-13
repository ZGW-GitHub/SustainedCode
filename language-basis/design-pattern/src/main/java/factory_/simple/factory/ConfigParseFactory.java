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

package factory_.simple.factory;

import factory_.simple.factory.parse.IConfigParse;
import factory_.simple.factory.parse.impl.JsonConfigParse;
import factory_.simple.factory.parse.impl.XmlConfigParse;
import factory_.simple.factory.parse.impl.YmlConfigParse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Snow
 * @date 2020/9/7 10:32 上午
 */
public class ConfigParseFactory {

	private static final Map<String, IConfigParse> CONFIG_PARSE_MAP = new HashMap<>();

	static {
		CONFIG_PARSE_MAP.put("json", new JsonConfigParse());
		CONFIG_PARSE_MAP.put("xml", new XmlConfigParse());
		CONFIG_PARSE_MAP.put("yml", new YmlConfigParse());
	}

	/**
	 * 当 ConfigParse 不能复用时使用该方法获取 ConfigParse <br/>
	 * 即：每次创建一个新的 ConfigParse 对象
	 *
	 * @param type 文件类型
	 * @return 解析器
	 */
	public static IConfigParse createParse(String type) {
		IConfigParse configParse = null;

		if ("json".equalsIgnoreCase(type)) {
			configParse = new JsonConfigParse();
		} else if ("xml".equalsIgnoreCase(type)) {
			configParse = new XmlConfigParse();
		} else if ("yml".equalsIgnoreCase(type)) {
			configParse = new YmlConfigParse();
		}

		return configParse;
	}

	/**
	 * 当 ConfigParse 能复用时使用该方法获取 ConfigParse
	 *
	 * @param type 文件类型
	 * @return 解析器
	 */
	public static IConfigParse createParseByReuse(String type) {
		return CONFIG_PARSE_MAP.get(type);
	}

}
