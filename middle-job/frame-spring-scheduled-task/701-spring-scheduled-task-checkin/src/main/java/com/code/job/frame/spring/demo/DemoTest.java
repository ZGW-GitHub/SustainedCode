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

package com.code.job.frame.spring.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2023/5/18 20:48
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class DemoTest {

	public static final String toEmail = "snow-official@outlook.com";

	@Resource
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@SneakyThrows
	@Scheduled(cron = "23 35 7 * * ?")
	public void demo() {
		System.err.println("定时任务执行时间：" + DateUtil.now());

		TimeUnit.SECONDS.sleep(RandomUtil.randomInt(60));

		// 签到
		String mailContent = checkIn();

		// 发送邮件
		sendEmail(mailContent);
	}

	private String checkIn() {
		JSONObject data = new JSONObject();
		data.set("token", "");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("cookie", "");

		// 创建请求参数
		HttpEntity<String> httpEntity = new HttpEntity<>(data.toString(), headers);

		try {
			String url = "";

			RestTemplate restTemplate = new RestTemplate();
			String       result       = restTemplate.postForObject(url, httpEntity, String.class);

			return "签到成功！\n\nresult ：\n" + result;
		} catch (Exception e) {
			log.warn("签到发生异常：{}", e.getMessage(), e);
			return "签到失败！\n\n原因：\n" + e.getMessage();
		}
	}

	private void sendEmail(String mailContent) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("自由之路助手" + '<' + fromEmail + '>');
		message.setTo(toEmail);
		message.setSubject("自由之路-签到");
		message.setText(mailContent);
		javaMailSender.send(message);
	}

}
