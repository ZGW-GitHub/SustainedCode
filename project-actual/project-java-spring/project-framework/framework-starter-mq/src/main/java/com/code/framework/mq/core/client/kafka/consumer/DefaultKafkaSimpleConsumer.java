package com.code.framework.mq.core.client.kafka.consumer;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

/**
 * @author 愆凡
 * @date 2022/6/16 15:47
 */
@Slf4j
@Setter
@Component
public class DefaultKafkaSimpleConsumer extends KafkaSimpleConsumer<String, String> {

	@Override
	protected void handleMessage(KafkaConsumer<String, String> consumer) {

	}

	@Override
	public String clientId() {
		return "defaultConsumer";
	}

}
