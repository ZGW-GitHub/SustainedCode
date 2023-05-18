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

package com.code.net.test;

import cn.hutool.json.JSONObject;
import com.code.net.SpringRestTemplateApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @author Snow
 * @date 2023/5/18 17:25
 */
public class PostTest extends SpringRestTemplateApplicationTest {

	@Test
	void checkInTest() {
		RestTemplate restTemplate = new RestTemplate();

		String url = "https://glados.rocks/api/user/checkin";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("cookie", "__stripe_mid=ac58181c-00d1-48d5-96c7-59275156e9f907d321; koa:sess=eyJ1c2VySWQiOjIxMDc1OCwiX2V4cGlyZSI6MTcwMzQ2NTcwMjk5MywiX21heEFnZSI6MjU5MjAwMDAwMDB9; koa:sess.sig=PyxyswyJSfVH_4IpUYdwXi4be5E");

		JSONObject data = new JSONObject();
		data.set("token", "glados.network");

		// 创建请求参数
		HttpEntity<String> httpEntity = new HttpEntity<>(data.toString(), headers);

		try {
			String result = restTemplate.postForObject(url, httpEntity, String.class);

			System.err.println("请求发送成功，result ：" + result);
		} catch (Exception e) {
			System.err.println("请求发送失败，错误信息：" + e.getMessage());
			e.printStackTrace();
		}
	}

}
