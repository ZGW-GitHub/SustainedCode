package com.code.framework.mq.core.client.kafka.producer;

import com.code.framework.mq.config.KafkaConfig;
import com.code.framework.mq.core.client.MqClient;
import jakarta.annotation.Resource;
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
public abstract class AbstractKafkaProducer<K, V> implements MqClient<KafkaProducer<K, V>, KafkaConfig.KafkaProducerConfig> {

	@Resource
	private KafkaConfig kafkaConfig;

	private KafkaProducer<K, V> client;

	@Override
	public final void afterSingletonsInstantiated() {
		KafkaConfig.KafkaProducerConfig kafkaProducerConfig = kafkaConfig.getProducer().get(clientId());

		builderClient(kafkaProducerConfig);
	}

	@Override
	public final void builderClient(KafkaConfig.KafkaProducerConfig kafkaProducerConfig) {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerConfig.getBootstrapServer());
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId());
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfig.getKeySerializer());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfig.getValueSerializer());
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);

		client = new KafkaProducer<>(properties);
	}

	public final KafkaProducer<K, V> client() {
		return client;
	}

}
