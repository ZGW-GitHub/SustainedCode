package com.code.framework.mq.core.client.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 愆凡
 * @date 2022/6/17 17:29
 */
@Slf4j
@Component
public class DefaultRocketProducer extends AbstractRocketProducer {

	@Value("${mq.rocketmq.name-server}")
	private String nameServer;

	@Override
	protected void customClient(DefaultMQProducer client) {
		client.setNamesrvAddr(nameServer);
		client.setProducerGroup("MY_DEFAULT_PRODUCER");
	}

}
