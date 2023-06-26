package com.code.framework.mq.core.client.kafka.consumer;

import com.code.framework.mq.config.KafkaConfig;
import com.code.framework.mq.core.client.MqClientBuilder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * @author 愆凡
 * @date 2022/6/16 15:31
 */
@Slf4j
public abstract class AbstractKafkaConsumer<K, V> implements MqClientBuilder<KafkaConsumer<K, V>, KafkaConfig.KafkaConsumerConfig> {

	@Resource
	private KafkaConfig kafkaConfig;

	private KafkaConsumer<K, V> client;

	@Override
	public final void afterSingletonsInstantiated() {
		KafkaConfig.KafkaConsumerConfig kafkaConsumerConfig = kafkaConfig.getConsumer().get(clientId());

		// 1、构建客户端
		builderClient(kafkaConsumerConfig);

		// 3、消费消息
		handleMessage(client);
	}

	@Override
	public final void builderClient(KafkaConfig.KafkaConsumerConfig kafkaConsumerConfig) {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerConfig.getBootstrapServer());
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerConfig.getGroupId());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfig.getKeySerializer());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfig.getValueSerializer());

		client = new KafkaConsumer<>(properties);

		client.subscribe(kafkaConsumerConfig.getSubscribe());
	}

	protected abstract void handleMessage(KafkaConsumer<K, V> consumer);

	public final KafkaConsumer<K, V> client() {
		return client;
	}

}
