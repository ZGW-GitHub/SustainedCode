package com.code.framework.mq.core.client.rocketmq.producer;

import com.code.framework.basic.exception.code.BizExceptionCode;
import com.code.framework.mq.config.RocketMQConfig;
import com.code.framework.mq.core.client.MqClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author 愆凡
 * @date 2022/6/16 16:46
 */
@Slf4j
public class RocketSimpleProducer implements MqClient<DefaultMQProducer, RocketMQConfig.RocketMQProducerConfig> {

	private final RocketMQConfig.RocketMQProducerConfig producerConfig;

	private final DefaultMQProducer client;

	public RocketSimpleProducer(RocketMQConfig.RocketMQProducerConfig producerConfig) {
		client = new DefaultMQProducer();
		client.setNamesrvAddr(producerConfig.getNameSrv());
		client.setProducerGroup(producerConfig.getGroup());

		this.producerConfig = producerConfig;
	}

	public DefaultMQProducer start() {
		try {
			client.start();
			log.debug("------ RocketMQ ------ Producer 启动成功, config : {}", producerConfig);
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ Producer 启动失败, config : {}", producerConfig, e);
			throw BizExceptionCode.COMMON_ERROR.exception();
		}

		return client;
	}

	@Override
	public final DefaultMQProducer client() {
		return client;
	}

}
