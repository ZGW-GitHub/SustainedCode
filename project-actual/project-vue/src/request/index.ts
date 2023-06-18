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

import axios from 'axios';

// 创建 axios 实例
const baseURL = 'http://127.0.0.1:65001/gateway'
const service = axios.create({
	baseURL: baseURL,
	timeout: 5000,
	headers: {
		"Content-type": "application/json;charset=utf-8"
	}
})

//请求拦截
service.interceptors.request.use((config) => {
	config.headers = config.headers || {}
	if (localStorage.getItem("token")) {
		config.headers.token = localStorage.getItem("token") || ""
	}
	return config
})

//响应拦截
service.interceptors.response.use(({data}) => {
	const code: number = data.data.code
	if (code != 200) {
		return Promise.reject(data)
	}
	return data
}, (err) => {
	console.log(err)
})

export default service