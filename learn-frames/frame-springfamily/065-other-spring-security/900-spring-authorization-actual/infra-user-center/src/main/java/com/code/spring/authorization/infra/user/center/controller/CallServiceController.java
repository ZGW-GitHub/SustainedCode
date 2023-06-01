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

package com.code.spring.authorization.infra.user.center.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Snow
 * @date 2023/5/30 21:28
 */
@Slf4j
@RestController
@RequestMapping("call")
public class CallServiceController {

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping("one/aaa")
	public String getServerOneAaa(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient) {
		return getServer("http://nouptoyou.com:65101/server/one/aaa", oAuth2AuthorizedClient);
	}

	@GetMapping("one/bbb")
	public String getServerOneBbb(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient) {
		return getServer("http://nouptoyou.com:65101/server/one/bbb", oAuth2AuthorizedClient);
	}

	@GetMapping("two/aaa")
	public String getServerTwoAaa(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient) {
		return getServer("http://nouptoyou.com:65102/server/two/aaa", oAuth2AuthorizedClient);
	}

	@GetMapping("two/bbb")
	public String getServerTwoBbb(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient) {
		return getServer("http://nouptoyou.com:65102/server/two/bbb", oAuth2AuthorizedClient);
	}

	/**
	 * 绑定 token ，请求微服务
	 */
	private String getServer(String url, OAuth2AuthorizedClient oAuth2AuthorizedClient) {
		// 获取 token
		String tokenValue = oAuth2AuthorizedClient.getAccessToken().getTokenValue();

		// 请求头
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + tokenValue);
		// 请求体
		HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
		// 发起请求
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
		} catch (RestClientException e) {
			log.error("调用服务发生异常：{}", e.getMessage(), e);
			return e.getMessage();
		}
		return responseEntity.getBody();
	}

}
