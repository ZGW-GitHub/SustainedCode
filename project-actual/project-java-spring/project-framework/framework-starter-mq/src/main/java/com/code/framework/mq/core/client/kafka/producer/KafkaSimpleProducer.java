package com.code.framework.mq.core.client.kafka.producer;

import com.code.framework.mq.config.KafkaConfig;
import com.code.framework.mq.core.client.MqClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

/**
 * @author 愆凡
 * @date 2022/6/16 14:49
 */
@Slf4j
@Getter
public class KafkaSimpleProducer<K, V> implements MqClient<KafkaProducer<K, V>, KafkaConfig.KafkaProducerConfig> {

	private final KafkaProducer<K, V> client;

	public KafkaSimpleProducer(KafkaConfig.KafkaProducerConfig producerConfig) {
		Properties properties = new Properties();
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, producerConfig.getClientId());
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerConfig.getBootstrapServer());
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerConfig.getKeySerializer());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerConfig.getValueSerializer());
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);

		client = new KafkaProducer<>(properties);
	}

	public final KafkaProducer<K, V> client() {
		return client;
	}

}
