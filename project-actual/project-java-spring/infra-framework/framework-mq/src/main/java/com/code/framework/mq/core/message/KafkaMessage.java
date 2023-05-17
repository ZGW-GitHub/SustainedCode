package com.code.framework.mq.core.message;

import com.code.framework.mq.core.event.KafkaSendEvent;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author 愆凡
 * @date 2022/6/17 16:24
 */
public interface KafkaMessage extends MqMessage {

	ProducerRecord<String, String> buildMessage(KafkaSendEvent event);

}
