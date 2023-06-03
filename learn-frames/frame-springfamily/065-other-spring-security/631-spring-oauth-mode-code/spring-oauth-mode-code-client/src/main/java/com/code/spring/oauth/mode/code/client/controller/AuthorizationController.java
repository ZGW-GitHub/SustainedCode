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

package com.code.spring.oauth.mode.code.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Snow
 * @date 2023/6/1 16:22
 */
@Slf4j
@RestController
public class AuthorizationController {

	// @Value("${spring.security.oauth2.client.provider.demoAuthorization.token-uri}")
	// private String tokenUri;
	//
	// @Value("${spring.security.oauth2.client.registration.demoClient.redirect-uri}")
	// private String redirectUri;
	//
	// @Value("${spring.security.oauth2.client.registration.demoClient.client-id}")
	// private String clientId;
	//
	// @Value("${spring.security.oauth2.client.registration.demoClient.client-secret}")
	// private String clientSecret;

	// @GetMapping("redirect")
	// public AuthorizationTokenInfo redirect(@RequestParam String code) {
	// 	HttpRequest httpRequest = HttpUtil.createPost(tokenUri);
	// 	httpRequest.header(Header.CONTENT_TYPE, ContentType.FORM_URLENCODED.getValue());
	// 	httpRequest.header(Header.AUTHORIZATION, "Basic " + Base64.encode(clientId + ":" + clientSecret));
	// 	httpRequest.form("grant_type", "authorization_code");
	// 	httpRequest.form("code", code);
	// 	httpRequest.form("redirect_uri", redirectUri);
	//
	// 	log.debug("【 Authorization Server 回调 】request url : {} , request form : {}", httpRequest.getUrl(), httpRequest.form());
	// 	try (HttpResponse httpResponse = httpRequest.execute()) {
	// 		log.debug("【 Authorization Server 回调 】response status : {} , response body : {}", httpResponse.getStatus(), httpResponse.body());
	// 		return JSONUtil.parseObj(httpResponse.body()).toBean(AuthorizationTokenInfo.class);
	// 	} catch (Exception e) {
	// 		log.error("【 Authorization Server 回调 】调用 server 获取 token 异常：{}", e.getMessage(), e);
	// 	}
	//
	// 	throw new RuntimeException("Token 获取失败");
	// }

	// @GetMapping("redirect")
	// public Map<String, Object> redirect(@RegisteredOAuth2AuthorizedClient("demoClient") OAuth2AuthorizedClient client) {
	// 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// 	Map<String, Object> map = new HashMap<>();
	// 	map.put("authentication", authentication);
	// 	// OAuth2AuthorizedClient 为敏感信息不应该返回前端
	// 	map.put("oAuth2AuthorizedClient", client);
	// 	return map;
	// }

	/**
	 * 防止 404
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}

}
