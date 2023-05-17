package com.code.framework.mq.core.client.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author 愆凡
 * @date 2022/6/16 14:58
 */
@Slf4j
@Component
public class DefaultKafkaProducer extends AbstractKafkaProducer<String, String> {

	@Value("${mq.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Override
	protected void customClient(Properties properties) {
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, "DEFAULT_PRODUCER");
	}

	@Override
	protected Class<? extends Serializer<String>> getKeySerializer() {
		return StringSerializer.class;
	}

	@Override
	protected Class<? extends Serializer<String>> getValueSerializer() {
		return StringSerializer.class;
	}

}
