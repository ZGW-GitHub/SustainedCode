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

package observer_.subject;

import observer_.observer.Observer;

/**
 * 被观察者接口
 *
 * @author Snow
 * @date 2020/9/9 10:45 上午
 */
public interface Subject {

	// 注册观察者
	void registerObserver(Observer observer);

	// 移除观察者
	void deleteObserver(Observer observer);

	// 通知观察者
	void notifyObservers(String msg);

}
