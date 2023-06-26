package com.code.framework.mq.core.client.rocketmq.producer;

import com.code.framework.basic.exception.code.BizExceptionCode;
import com.code.framework.mq.config.RocketMQConfig;
import com.code.framework.mq.core.client.MqClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author 愆凡
 * @date 2022/6/16 16:46
 */
@Slf4j
public abstract class AbstractRocketProducer implements MqClient<DefaultMQProducer, RocketMQConfig.RocketMQProducerConfig> {

	@Resource
	private RocketMQConfig rocketMQConfig;

	private DefaultMQProducer client;

	@Override
	public final void afterSingletonsInstantiated() {
		RocketMQConfig.RocketMQProducerConfig producerConfig = rocketMQConfig.getProducer().get(clientId());

		builderClient(producerConfig);

		try {
			client.start();
			log.debug("------ RocketMQ ------ RocketMQ Producer 启动成功");
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ RocketMQ Producer 启动失败 ！！！", e);
			throw BizExceptionCode.COMMON_ERROR.exception();
		}
	}

	@Override
	public final void builderClient(RocketMQConfig.RocketMQProducerConfig producerConfig) {
		client = new DefaultMQProducer();
		client.setNamesrvAddr(producerConfig.getNameSrv());
		client.setProducerGroup(producerConfig.getGroup());
	}

	@Override
	public final DefaultMQProducer client() {
		return client;
	}

}
