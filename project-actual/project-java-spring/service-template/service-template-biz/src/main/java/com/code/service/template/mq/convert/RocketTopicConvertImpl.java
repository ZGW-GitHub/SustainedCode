package com.code.service.template.mq.convert;

import com.code.framework.common.exception.BizException;
import com.code.framework.common.exception.enums.ExceptionCodeEnum;
import com.code.framework.mq.core.convert.KafkaTopicConvert;
import com.code.framework.mq.core.message.KafkaMessage;
import com.code.service.template.mq.message.TestMessage;
import com.code.service.template.mq.properties.RocketTopicProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 愆凡
 * @date 2022/7/6 21:46
 */
@Slf4j
@Component
public class RocketTopicConvertImpl implements KafkaTopicConvert {

	@Resource
	private RocketTopicProperties topicProperties;

	@Override
	public String convert(KafkaMessage message) {
		if (message instanceof TestMessage) {
			return topicProperties.getTestTopic();
		} else {
			throw new BizException(ExceptionCodeEnum.COMMON_ERROR);
		}
	}

}
