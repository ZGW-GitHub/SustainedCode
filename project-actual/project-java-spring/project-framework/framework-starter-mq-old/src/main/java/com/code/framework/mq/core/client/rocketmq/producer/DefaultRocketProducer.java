package com.code.framework.mq.core.client.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 愆凡
 * @date 2022/6/17 17:29
 */
@Slf4j
@Component
public class DefaultRocketProducer extends AbstractRocketProducer {

	@Override
	public String clientId() {
		return "defaultProducer";
	}

}
