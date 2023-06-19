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

import service from "@/request/index";

// 登录接口
export function login() {
	return service({
		url: "/gateway/login",
		method: "POST"
	})
}

// 测试接口
export function templateTest(content: Object) {
	return service({
		url: "/gateway",
		method: "POST",
		data: {
			api: "template.test",
			version: "1.0.0",
			content: JSON.stringify(content)
		}
	})
}

// 测试接口
export function templateSave(content: Object) {
	return service({
		url: "/gateway",
		method: "POST",
		data: {
			api: "template.save",
			version: "1.0.0",
			content: JSON.stringify(content)
		}
	})
}