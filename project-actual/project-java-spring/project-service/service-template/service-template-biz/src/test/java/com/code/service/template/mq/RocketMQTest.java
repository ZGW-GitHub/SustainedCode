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

package com.code.service.template.mq;

import com.code.framework.mq.client.rocketmq.RocketMQConfig;
import com.code.framework.mq.client.rocketmq.producer.RocketSimpleProducer;
import com.code.service.template.ServiceTemplateApplicationTest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2023/6/27 13:20
 */
@Slf4j
public class RocketMQTest extends ServiceTemplateApplicationTest {

	@Resource
	private RocketMQConfig rocketMQConfig;

	@Test
	void rocketMqTest() {
		RocketMQConfig.RocketMQProducerConfig producerConfig = rocketMQConfig.getProducer().get("");
		DefaultMQProducer defaultMQProducer = new RocketSimpleProducer(producerConfig).start();
	}

}
