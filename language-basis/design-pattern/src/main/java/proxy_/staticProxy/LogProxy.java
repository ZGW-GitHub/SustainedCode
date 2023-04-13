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

package proxy_.staticProxy;

/**
 * @author Snow
 */
public class LogProxy implements Movable {

	private final Movable tanK;

	public LogProxy(Movable tanK) {
		this.tanK = tanK;
	}

	// 在调用被代理类的方法之前/之后，添加逻辑
	@Override
	public void run() {

		System.out.println("移动之前的日志：要移动了");

		tanK.run();

		System.out.println("移动之后的日志：移动结束");

	}

}
