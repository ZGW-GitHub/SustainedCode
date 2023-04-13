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

package dan.li;

/**
 * 懒汉式
 * 适用于多线程，但效率不高
 *
 * @author Snow
 */
public class SingletonB {

	private static SingletonB singleton = null;

	private SingletonB() {
	}

	// 添加了 synchronized 关键字，进行同步
	public static synchronized SingletonB getInstance() {
		if (singleton == null) {
			singleton = new SingletonB();
		}
		return singleton;
	}

}
