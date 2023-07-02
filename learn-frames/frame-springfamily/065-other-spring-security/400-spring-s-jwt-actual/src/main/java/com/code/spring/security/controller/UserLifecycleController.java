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

package com.code.spring.security.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.json.JSONObject;
import com.code.spring.security.controller.domain.UserRegisterReq;
import com.code.spring.security.controller.domain.UserRegisterResp;
import com.code.spring.security.dal.dos.SysUser;
import com.code.spring.security.dal.mapper.SysUserMapper;
import com.code.spring.security.service.SysUserService;
import com.code.spring.security.util.JWTUtil;
import com.code.spring.security.util.PasswordUtil;
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
	private SysUserService sysUserService;

	@Resource
	private SysUserMapper sysUserMapper;

	@ResponseBody
	@PostMapping("register")
	public UserRegisterResp register(UserRegisterReq userRegisterReq) {
		SysUser sysUser = sysUserService.findByAccount(userRegisterReq.getAccount());
		if (sysUser != null) {
			throw new RuntimeException("用户已存在");
		}

		String salt = PasswordUtil.generateSalt();
		sysUser = new SysUser()
				.setAccount(userRegisterReq.getAccount())
				.setSalt(salt)
				.setPassword(PasswordUtil.encode(userRegisterReq.getPassword(), salt));
		sysUserMapper.insert(sysUser);

		return new UserRegisterResp()
				.setAccount(userRegisterReq.getAccount())
				.setToken(JWTUtil.generateToken(sysUser.getAccount()))
				.setRefreshToken(JWTUtil.generateRefreshToken(sysUser.getAccount()));
	}

	@GetMapping(path = "login", produces = MediaType.TEXT_HTML_VALUE)
	public String loginPage() {
		return "login";
	}

	@ResponseBody
	@RequestMapping("loginSuccess")
	public String login() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof SysUser sysUser)) {
			throw new RuntimeException("登录信息异常");
		}

		String token = JWTUtil.generateToken(sysUser.getAccount());
		String refreshToken = JWTUtil.generateRefreshToken(sysUser.getAccount());

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
