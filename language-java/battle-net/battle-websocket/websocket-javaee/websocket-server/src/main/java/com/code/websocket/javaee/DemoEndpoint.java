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

package com.code.websocket.javaee;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 注意：使用的是 jakarta-ee ，因此 Tomcat 也要选已经支持 jakarta-ee 的版本
 *
 * @author Snow
 * @date 2022/10/12 11:00
 */
@Slf4j
@ServerEndpoint("/websocket/{username}")
public class DemoEndpoint {

	private static final AtomicInteger ONLINE_COUNT = new AtomicInteger();
	private static final Map<String, DemoEndpoint> CLIENTS = new ConcurrentHashMap<>();

	private Session session;

	private String username;

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) {
		this.session = session;
		this.username = username;
		DemoEndpoint.ONLINE_COUNT.incrementAndGet();
		DemoEndpoint.CLIENTS.put(username, this);

		System.out.println(username + " 上线了，当前活跃数：" + DemoEndpoint.ONLINE_COUNT);
	}

	@OnMessage
	public void onMessage(String message) {
		String msg = "收到 " + this.username + " 发送的消息：" + message;

		DemoEndpoint.CLIENTS.forEach((username, endpoint) -> {
			if (username.equals(this.username)) {
				return;
			}
			endpoint.session.getAsyncRemote().sendText(msg);
		});

		System.out.println(msg);
	}

	@OnClose
	public void onClose() {
		DemoEndpoint.ONLINE_COUNT.decrementAndGet();
		DemoEndpoint.CLIENTS.remove(this.username);

		System.out.println(username + " 离线了，当前活跃数：" + DemoEndpoint.ONLINE_COUNT);
	}

	@OnError
	public void onError(Throwable e) {
		log.error(username + "发生异常：", e);
	}

}
