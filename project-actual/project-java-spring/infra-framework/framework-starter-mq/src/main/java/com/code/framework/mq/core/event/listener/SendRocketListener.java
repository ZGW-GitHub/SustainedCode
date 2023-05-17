package com.code.framework.mq.core.event.listener;

import com.code.framework.mq.core.client.rocketmq.producer.DefaultRocketProducer;
import com.code.framework.mq.core.event.RocketSendEvent;
import com.code.framework.mq.core.message.RocketMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author 愆凡
 * @date 2022/6/16 11:35
 */
@Slf4j
@Component
public class SendRocketListener {

	@Resource
	private DefaultRocketProducer defaultRocketProducer;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
	public void listener(RocketSendEvent event) {
		RocketMessage source = event.getSource();

		Message message = source.buildMessage(event);

		try {
			defaultRocketProducer.client().send(message);
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			log.error("发送消息到 RocketMQ 发生异常：{}", e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
