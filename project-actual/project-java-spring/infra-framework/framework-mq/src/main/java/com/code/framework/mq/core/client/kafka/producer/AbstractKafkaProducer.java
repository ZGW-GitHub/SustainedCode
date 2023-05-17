package com.code.framework.mq.core.client.kafka.producer;

import com.code.framework.mq.core.client.MqClientBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

/**
 * @author 愆凡
 * @date 2022/6/16 14:49
 */
@Slf4j
@Getter
public abstract class AbstractKafkaProducer<K, V> implements MqClientBuilder<KafkaProducer<K, V>> {

	private KafkaProducer<K, V> client;

	@Override
	public final void afterSingletonsInstantiated() {
		builderClient();
	}

	@Override
	public final void builderClient() {
		Properties properties = new Properties();
		loadDefaultProperties(properties);
		customClient(properties);
		serializerConfig(properties);

		client = new KafkaProducer<>(properties);
	}

	private void loadDefaultProperties(Properties properties) {
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);
	}

	protected abstract void customClient(Properties properties);

	private void serializerConfig(Properties properties) {
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, getKeySerializer().getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getValueSerializer().getName());
	}

	protected abstract Class<? extends Serializer<K>> getKeySerializer();

	protected abstract Class<? extends Serializer<K>> getValueSerializer();

	public final KafkaProducer<K, V> client() {
		return client;
	}

}
