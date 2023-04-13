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

package mo.ban;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2021/3/12 17:45
 */
@Slf4j
public abstract class Command {
	
	void log() {
		System.err.println("执行命令：");
	}

	/**
	 * 提交命令
	 */
	abstract void commit();

	/**
	 * 查询命令执行状态
	 */
	abstract void state();

	/**
	 * 执行命令 - 模板方法
	 */
	final void executeCommand() {
		log();
		commit();
		state();
	}

}
