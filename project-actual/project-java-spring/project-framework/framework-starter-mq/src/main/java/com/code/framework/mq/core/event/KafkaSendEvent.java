package com.code.framework.mq.core.event;

import com.code.framework.mq.core.convert.KafkaTopicConvert;
import com.code.framework.mq.message.KafkaMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 愆凡
 * @date 2022/6/16 23:34
 */
@Slf4j
public class KafkaSendEvent extends AbstractMqEvent<KafkaMessage> {

	@Resource
	private List<KafkaTopicConvert> topicConverts;

	public KafkaSendEvent(KafkaMessage message) {
		List<String> topics = topicConverts.stream().map(c -> c.convert(source)).toList();
		if (topics.size() != 1) {
			return;
		}

		source = message;
		topic = topics.get(0);
	}

}
