package com.code.framework.mq.core.client.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 愆凡
 * @date 2022/6/16 14:58
 */
@Slf4j
@Component
public class DefaultKafkaProducer extends AbstractKafkaProducer<String, String> {

	@Override
	public String clientId() {
		return "defaultProducer";
	}

}
