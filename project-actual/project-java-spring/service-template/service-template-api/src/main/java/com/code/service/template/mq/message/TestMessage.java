package com.code.service.template.mq.message;

import com.code.framework.mq.core.event.KafkaSendEvent;
import com.code.framework.mq.core.event.RocketSendEvent;
import com.code.framework.mq.core.message.KafkaMessage;
import com.code.framework.mq.core.message.RocketMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.rocketmq.common.message.Message;

/**
 * @author 愆凡
 * @date 2022/6/17 11:22
 */
@Slf4j
@Data
public class TestMessage implements KafkaMessage, RocketMessage {

	private String name;
	private String age;

	@Override
	public ProducerRecord<String, String> buildMessage(KafkaSendEvent event) {
		// String value = JSONUtil.parseObj(this).toJSONString(0);

		return new ProducerRecord<>(event.getTopic(), "value");
	}

	@Override
	public Message buildMessage(RocketSendEvent rocketSendEvent) {
		return null;
	}

}
