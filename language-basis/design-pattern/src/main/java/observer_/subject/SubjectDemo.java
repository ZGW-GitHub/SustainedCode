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

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者一
 *
 * @author Snow
 * @date 2020/9/9 10:51 上午
 */
public class SubjectDemo implements Subject {

	private final List<Observer> observers = new ArrayList<>();

	// 注册观察者
	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	// 移除观察者
	@Override
	public void deleteObserver(Observer observer) {
		observers.remove(observer);
	}

	// 通知观察者
	@Override
	public void notifyObservers(String msg) {
		observers.forEach(observer -> observer.update(msg));
	}
}
