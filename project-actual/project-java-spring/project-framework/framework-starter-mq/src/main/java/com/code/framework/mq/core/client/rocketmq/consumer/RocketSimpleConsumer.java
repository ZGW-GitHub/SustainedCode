package com.code.framework.mq.core.client.rocketmq.consumer;

import com.code.framework.basic.exception.code.BizExceptionCode;
import com.code.framework.mq.config.RocketMQConfig;
import com.code.framework.mq.core.client.MqClient;
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
public class RocketSimpleConsumer implements MqClient<DefaultMQPushConsumer, RocketMQConfig.RocketMQConsumerConfig> {

	private final RocketMQConfig.RocketMQConsumerConfig consumerConfig;

	private DefaultMQPushConsumer client;

	public RocketSimpleConsumer(RocketMQConfig.RocketMQConsumerConfig consumerConfig) {
		client = new DefaultMQPushConsumer();
		client.setNamesrvAddr(consumerConfig.getNameSrv());
		client.setConsumerGroup(consumerConfig.getGroup());
		client.setMessageModel(MessageModel.valueOf(consumerConfig.getMessageModel()));
		client.setConsumeFromWhere(ConsumeFromWhere.valueOf(consumerConfig.getConsumeFromWhere()));

		this.consumerConfig = consumerConfig;
	}

	public DefaultMQPushConsumer start() {
		try {
			client.subscribe(consumerConfig.getSubscribe(), consumerConfig.getSubExpression());

			client.start();
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ RocketMQ Consumer 启动失败 ！！！", e);
			throw BizExceptionCode.COMMON_ERROR.exception();
		}
		return client;
	}

	@Override
	public final DefaultMQPushConsumer client() {
		return client;
	}

}
