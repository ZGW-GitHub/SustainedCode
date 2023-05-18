package com.code.framework.mq.core.client.kafka.consumer;

import com.code.framework.mq.core.client.MqClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Properties;

/**
 * @author 愆凡
 * @date 2022/6/16 15:31
 */
@Slf4j
public abstract class AbstractKafkaConsumer<K, V> implements MqClientBuilder<KafkaConsumer<K, V>> {

	private KafkaConsumer<K, V> client;

	@Override
	public final void afterSingletonsInstantiated() {
		// 1、构建客户端
		builderClient();

		// 2、配置订阅
		subscribeConfig(client);

		// 3、消费消息
		handleMessage(client);
	}

	@Override
	public final void builderClient() {
		Properties properties = new Properties();
		loadDefaultProperties(properties);
		customClient(properties);
		serializerConfig(properties);

		client = new KafkaConsumer<>(properties);
	}

	private void loadDefaultProperties(Properties properties) {
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	}

	protected abstract void customClient(Properties properties);

	private void serializerConfig(Properties properties) {
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, getKeySerializer().getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getValueSerializer().getName());
	}

	protected abstract Class<? extends Deserializer<K>> getKeySerializer();

	protected abstract Class<? extends Deserializer<K>> getValueSerializer();

	protected abstract void subscribeConfig(KafkaConsumer<K, V> consumer);

	protected abstract void handleMessage(KafkaConsumer<K, V> consumer);

	public final KafkaConsumer<K, V> client() {
		return client;
	}

}
