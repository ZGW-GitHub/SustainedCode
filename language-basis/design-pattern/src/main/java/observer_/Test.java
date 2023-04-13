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

package observer_;

import observer_.observer.ObserverDemoOne;
import observer_.subject.SubjectDemo;

/**
 * @author Snow
 * @date 2020/9/9 10:58 上午
 */
public class Test {
	public static void main(String[] args) {

		// 创建被观察者
		SubjectDemo subjectDemo = new SubjectDemo();

		// 注册观察者
		subjectDemo.registerObserver(new ObserverDemoOne());
		subjectDemo.registerObserver(new ObserverDemoOne());

		// 通知观察者
		subjectDemo.notifyObservers("test");

	}
}
