package com.code.framework.mq.core.convert;

import com.code.framework.mq.message.MqMessage;

/**
 * @author 愆凡
 * @date 2022/7/6 17:48
 */
public interface TopicConvert<M extends MqMessage> {

	String convert(M message);

}
