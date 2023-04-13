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
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2020/9/7 10:30 上午
 */
@SuppressWarnings("all")
public class DemoTest {

	/**
	 * 加载并解析配置文件
	 *
	 * @param filePath filePath
	 */
	@Test
	void load(String filePath) {
		String       fileType    = getFileType(filePath);
		IConfigParse configParse = ConfigParseFactory.createParse(fileType);

		System.err.println(configParse.doParse(filePath));
	}

	/**
	 * 根据文件路径获取文件类型
	 *
	 * @param filePath filePath
	 * @return str
	 */
	public String getFileType(String filePath) {
		return "json";
	}

}
