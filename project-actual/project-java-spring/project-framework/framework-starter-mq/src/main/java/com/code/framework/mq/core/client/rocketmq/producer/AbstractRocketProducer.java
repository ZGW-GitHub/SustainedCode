package com.code.framework.mq.core.client.rocketmq.producer;

import com.code.framework.basic.exception.core.BizExceptionCode;
import com.code.framework.mq.core.client.MqClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author 愆凡
 * @date 2022/6/16 16:46
 */
@Slf4j
public abstract class AbstractRocketProducer implements MqClientBuilder<DefaultMQProducer> {

	private DefaultMQProducer client;

	@Override
	public final void afterSingletonsInstantiated() {
		builderClient();

		try {
			client.start();
			log.debug("------ RocketMQ ------ RocketMQ Producer 启动成功");
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ RocketMQ Producer 启动失败 ！！！", e);
			throw BizExceptionCode.COMMON_ERROR.exception();
		}
	}

	@Override
	public final void builderClient() {
		client = new DefaultMQProducer();

		customClient(client);
	}

	/**
	 * 自定义客户端
	 *
	 * @param client 客户端
	 */
	protected abstract void customClient(DefaultMQProducer client);

	@Override
	public final DefaultMQProducer client() {
		return client;
	}

}
