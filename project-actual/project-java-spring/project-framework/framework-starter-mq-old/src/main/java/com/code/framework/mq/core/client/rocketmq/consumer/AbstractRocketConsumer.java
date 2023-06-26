package com.code.framework.mq.core.client.rocketmq.consumer;

import com.code.framework.basic.exception.code.BizExceptionCode;
import com.code.framework.mq.config.RocketMQConfig;
import com.code.framework.mq.core.client.MqClientBuilder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @author 愆凡
 * @date 2022/6/16 16:46
 */
@Slf4j
public abstract class AbstractRocketConsumer implements MqClientBuilder<DefaultMQPushConsumer, RocketMQConfig.RocketMQConsumerConfig> {

	@Resource
	private RocketMQConfig rocketMQConfig;

	private DefaultMQPushConsumer client;

	@Override
	public final void afterSingletonsInstantiated() {
		RocketMQConfig.RocketMQConsumerConfig consumerConfig = rocketMQConfig.getConsumer().get(clientId());

		// 1、构建客户端
		builderClient(consumerConfig);

		// 3、消费消息
		handleMessage(client);

		try {
			client.start();
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ RocketMQ Consumer 启动失败 ！！！", e);
			throw BizExceptionCode.COMMON_ERROR.exception();
		}
	}

	@Override
	public final void builderClient(RocketMQConfig.RocketMQConsumerConfig consumerConfig) {
		client = new DefaultMQPushConsumer();
		client.setNamesrvAddr(consumerConfig.getNameSrv());
		client.setConsumerGroup(consumerConfig.getGroup());
		client.subscribe();
		client.setMessageModel(MessageModel.valueOf(consumerConfig.getMessageModel()));
		client.setConsumeFromWhere(ConsumeFromWhere.valueOf(consumerConfig.getConsumeFromWhere()));
	}

	/**
	 * 处理消息
	 *
	 * @param consumer Consumer
	 */
	protected abstract void handleMessage(DefaultMQPushConsumer consumer);

	@Override
	public final DefaultMQPushConsumer client() {
		return client;
	}

}
