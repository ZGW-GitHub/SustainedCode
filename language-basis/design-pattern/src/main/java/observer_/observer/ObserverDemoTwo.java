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

package observer_.observer;

/**
 * 观察者二
 *
 * @author Snow
 * @date 2020/9/9 10:57 上午
 */
public class ObserverDemoTwo implements Observer {

	@Override
	public void update(String message) {
		System.out.println("[ ObserverDemoTwo ] - 收到通知：" + message);
	}

}
