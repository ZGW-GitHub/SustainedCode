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
 * 适用于多线程,双重检查加锁.   --->      推荐
 *
 * @author Snow
 */
public class SingletonC {

	private static SingletonC singleton = null;

	private SingletonC() {
	}

	// 添加了 synchronized 关键字，并进行双重检查
	public static SingletonC getInstance() {
		if (singleton == null) {
			synchronized (SingletonC.class) {
				if (singleton == null) {
					singleton = new SingletonC();
				}
			}
		}
		return singleton;
	}

}
