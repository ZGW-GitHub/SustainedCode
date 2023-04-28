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

package com.code.rocketmq.producer;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.code.rocketmq.RocketMqApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.MimeType;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Snow
 * @date 2022/7/1 15:30
 */
@Slf4j
class ProducerTest extends RocketMqApplicationTest {

	@Resource
	private StreamBridge streamBridge;

	@Test
	void producer() throws InterruptedException {
		JSONObject msg = new JSONObject();
		msg.put("key", DateUtil.now());

		streamBridge.send("test-in-0", msg.toJSONString());

		TimeUnit.SECONDS.sleep(5000);
	}

}
