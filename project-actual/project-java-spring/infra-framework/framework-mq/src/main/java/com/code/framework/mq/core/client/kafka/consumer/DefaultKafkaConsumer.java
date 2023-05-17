package com.code.framework.mq.core.client.kafka.consumer;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

/**
 * @author 愆凡
 * @date 2022/6/16 15:47
 */
@Slf4j
@Setter
@Component
public class DefaultKafkaConsumer extends AbstractKafkaConsumer<String, String> {

	@Value("${mq.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Override
	protected void customClient(Properties properties) {
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "DEFAULT_CONSUMER");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "DEFAULT_GROUP");
	}

	@Override
	protected void subscribeConfig(KafkaConsumer<String, String> consumer) {
		consumer.subscribe(Collections.singleton("test_topic"));
	}

	@Override
	protected void handleMessage(KafkaConsumer<String, String> consumer) {

	}

	@Override
	protected Class<? extends Deserializer<String>> getKeySerializer() {
		return StringDeserializer.class;
	}

	@Override
	protected Class<? extends Deserializer<String>> getValueSerializer() {
		return StringDeserializer.class;
	}

}
