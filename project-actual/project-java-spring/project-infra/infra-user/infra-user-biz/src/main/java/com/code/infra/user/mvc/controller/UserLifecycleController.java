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

package com.code.infra.user.mvc.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.json.JSONObject;
import com.code.framework.basic.util.BeanUtil;
import com.code.framework.basic.util.PasswordUtil;
import com.code.infra.user.mvc.controller.domain.RegisterReq;
import com.code.infra.user.mvc.controller.domain.RegisterResp;
import com.code.infra.user.mvc.dal.domain.dos.UserInfoDO;
import com.code.infra.user.mvc.dal.mapper.UserInfoMapper;
import com.code.infra.user.mvc.service.UserInfoService;
import com.code.infra.user.mvc.service.domain.*;
import com.code.infra.user.pojo.TokenInfoPOJO;
import com.code.infra.user.util.JWTUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Snow
 * @date 2023/6/29 11:13
 */
@Slf4j
@Controller
@RequestMapping("userLifecycle")
public class UserLifecycleController {

	@Resource
	private UserInfoService userInfoService;

	@Resource
	private UserInfoMapper sysUserMapper;

	@ResponseBody
	@PostMapping("register")
	public RegisterResp register(RegisterReq registerReq) {
		UserInfoDetailDTO userInfoDetailDTO = userInfoService.findUserInfo(new UserInfoDetailBO().setAccount(registerReq.getAccount()));
		if (userInfoDetailDTO != null) {
			throw new RuntimeException("用户已存在");
		}

		String salt = PasswordUtil.generateSalt();
		UserInfoDO userInfoDO = new UserInfoDO()
				.setAccount(registerReq.getAccount())
				.setSalt(salt)
				.setPassword(PasswordUtil.encode(registerReq.getPassword(), salt));
		sysUserMapper.insert(userInfoDO);

		TokenInfoDTO tokenInfoDTO = userInfoService.findTokenInfo(new TokenInfoBO().setAccount(registerReq.getAccount()));
		TokenInfoPOJO tokenInfoPOJO = BeanUtil.map(tokenInfoDTO, TokenInfoPOJO::new);

		return new RegisterResp()
				.setAccount(registerReq.getAccount())
				.setToken(JWTUtil.generateToken(tokenInfoPOJO, registerReq.getAccount()))
				.setRefreshToken(JWTUtil.generateRefreshToken(tokenInfoPOJO, registerReq.getAccount()));
	}

	@GetMapping(path = "login", produces = MediaType.TEXT_HTML_VALUE)
	public String loginPage() {
		return "login";
	}

	@ResponseBody
	@RequestMapping("loginSuccess")
	public String login() {
		Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (!(details instanceof AuthInfoDTO authInfoDTO)) {
			throw new RuntimeException("认证中的用户信息类型不正确");
		}

		TokenInfoDTO tokenInfoDTO = userInfoService.findTokenInfo(new TokenInfoBO().setAccount(authInfoDTO.getAccount()));
		TokenInfoPOJO tokenInfoPOJO = BeanUtil.map(tokenInfoDTO, TokenInfoPOJO::new);

		String token = JWTUtil.generateToken(tokenInfoPOJO, authInfoDTO.getAccount());
		String refreshToken = JWTUtil.generateRefreshToken(tokenInfoPOJO, authInfoDTO.getAccount());

		JSONObject result = new JSONObject();
		result.set("token", token);
		result.set("refreshToken", refreshToken);
		return result.toString();
	}

	@ResponseBody
	@PostMapping("logout")
	public String logout() {
		return "SUCCESS";
	}

	@ResponseBody
	@PostMapping("writeOff")
	public String writeOff() {
		return "SUCCESS";
	}

	@ResponseBody
	@GetMapping("captcha")
	public Map<String, Object> captcha(HttpSession session) {
		// 图形验证码：宽、高、验证码字符数、干扰线宽度
		ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 40, 4, 2);
		// 这里应该返回一个统一响应类，暂时使用 map 代替
		Map<String, Object> result = new HashMap<>();
		result.put("code", HttpStatus.OK.value());
		result.put("success", true);
		result.put("message", "获取验证码成功.");
		result.put("data", captcha.getImageBase64Data());
		// 存入 session 中 TODO 存入 redis 中
		session.setAttribute("captcha", captcha.getCode());
		return result;
	}

}
