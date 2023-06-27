package com.code.framework.mq.core.event.listener;

import com.code.framework.mq.core.client.kafka.producer.DefaultKafkaProducer;
import com.code.framework.mq.core.event.KafkaSendEvent;
import com.code.framework.mq.message.KafkaMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author 愆凡
 * @date 2022/6/16 11:35
 */
@Slf4j
public class SendKafkaListener {

	@Resource
	private DefaultKafkaProducer defaultKafkaProducer;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
	public void listener(KafkaSendEvent event) {
		KafkaMessage source = event.getSource();

		ProducerRecord<String, String> message = source.buildMessage(event);

		defaultKafkaProducer.client().send(message);
	}

}
