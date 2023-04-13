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
import factory_.factory.method.parse.IConfigParse;

/**
 * @author Snow
 * @date 2020/9/7 10:30 上午
 */
public class ConfigSource {

	public String load(String filePath) {
		String fileType = getFileType(filePath);

		// 获取工厂
		IConfigParseFactory parseFactory = ConfigParseFactoryMap.getParseFactoryByReuse(fileType);
		// 根据工厂获取 parse
		IConfigParse parse = parseFactory.createParse();

		return parse.doParse(filePath);
	}

	// 根据文件路径获取文件类型
	public String getFileType(String filePath) {
		return "json";
	}

}
