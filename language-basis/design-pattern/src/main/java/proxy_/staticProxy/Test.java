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
public class Test {

	public static void main(String[] args) {

		Movable proxy;

		// 先记录日志，再记录时间
		/**
		 * 移动之前：1573273220750
		 * 移动之前的日志：要移动了
		 * 坦克移动中...
		 * 移动之后的日志：移动结束
		 * 移动之后：1573273224464
		 */
		proxy = new TimeProxy(new LogProxy(new TanK()));
		proxy.run();

		System.out.println("++++++++++++++++++++++++++++++");

		// 先记录时间，再记录日志
		/**
		 * 移动之前的日志：要移动了
		 * 移动之前：1573273355400
		 * 坦克移动中...
		 * 移动之后：1573273359255
		 * 移动之后的日志：移动结束
		 */
		proxy = new LogProxy(new TimeProxy(new TanK()));
		proxy.run();

	}

}
