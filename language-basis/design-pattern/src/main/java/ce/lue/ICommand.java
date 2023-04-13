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

package ce.lue;

/**
 * 用来约束、标识策略
 *
 * @author Snow
 * @date 2021/3/12 15:18
 */
public interface ICommand {

	/**
	 * 获取具体策略
	 *
	 * @param str str
	 * @return {@link ICommand}
	 */
	@SuppressWarnings("all")
	static ICommand getInstance(String str) {
		if ("select".equals(str)) {
			return new SelectCommand();
		}
		if ("delete".equals(str)) {
			return new DeleteCommand();
		}

		throw new RuntimeException("该命令没有对应的策略来处理");
	}

	/**
	 * 提交命令
	 *
	 * @param str str
	 */
	void commit(String str);

	/**
	 * 查询命令执行状态
	 */
	void state();

}
