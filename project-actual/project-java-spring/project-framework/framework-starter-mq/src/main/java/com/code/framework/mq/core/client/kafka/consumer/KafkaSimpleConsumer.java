package com.code.framework.mq.core.client.kafka.consumer;

import com.code.framework.mq.config.KafkaConfig;
import com.code.framework.mq.core.client.MqClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * @author 愆凡
 * @date 2022/6/16 15:31
 */
@Slf4j
public class KafkaSimpleConsumer<K, V> implements MqClient<KafkaConsumer<K, V>, KafkaConfig.KafkaConsumerConfig> {

	private final KafkaConsumer<K, V> client;

	public KafkaSimpleConsumer(KafkaConfig.KafkaConsumerConfig consumerConfig) {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerConfig.getClientId());
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerConfig.getBootstrapServer());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerConfig.getGroupId());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerConfig.getKeySerializer());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerConfig.getValueSerializer());

		client = new KafkaConsumer<>(properties);

		client.subscribe(consumerConfig.getSubscribe());
	}

	public final KafkaConsumer<K, V> client() {
		return client;
	}

}
