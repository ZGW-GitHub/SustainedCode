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
 * 只适用于单线程模式
 *
 * @author Snow
 */
public class SingletonA {

	// 用于保存单例对象
	private static SingletonA singleton = null;

	private SingletonA() {
	}

	public static SingletonA getInstance() {
		if (singleton == null) {
			singleton = new SingletonA();
		}
		return singleton;
	}

}
