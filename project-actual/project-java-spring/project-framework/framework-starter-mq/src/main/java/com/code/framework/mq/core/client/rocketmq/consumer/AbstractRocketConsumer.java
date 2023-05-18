package com.code.framework.mq.core.client.rocketmq.consumer;

import com.code.framework.basic.exception.BizException;
import com.code.framework.basic.result.code.ResultCodeEnum;
import com.code.framework.mq.core.client.MqClientBuilder;
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
public abstract class AbstractRocketConsumer implements MqClientBuilder<DefaultMQPushConsumer> {

	private DefaultMQPushConsumer client;

	@Override
	public final void afterSingletonsInstantiated() {
		// 1、构建客户端
		builderClient();

		// 2、配置订阅
		subscribeConfig(client);

		// 3、消费消息
		handleMessage(client);

		try {
			client.start();
		} catch (MQClientException e) {
			log.error("------ RocketMQ ------ RocketMQ Consumer 启动失败 ！！！", e);
			throw new BizException(ResultCodeEnum.COMMON_ERROR);
		}
	}

	@Override
	public final void builderClient() {
		client = new DefaultMQPushConsumer();
		client.setMessageModel(MessageModel.CLUSTERING);
		client.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		customClient(client);
	}

	/**
	 * 自定义客户端
	 *
	 * @param client 客户端
	 */
	protected abstract void customClient(DefaultMQPushConsumer client);

	/**
	 * 订阅 Topic
	 *
	 * @param consumer Consumer
	 */
	protected abstract void subscribeConfig(DefaultMQPushConsumer consumer);

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
