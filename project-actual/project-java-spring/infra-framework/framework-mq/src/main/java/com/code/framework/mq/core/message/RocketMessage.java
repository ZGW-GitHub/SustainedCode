package com.code.framework.mq.core.message;

import com.code.framework.mq.core.event.RocketSendEvent;
import org.apache.rocketmq.common.message.Message;

/**
 * @author 愆凡
 * @date 2022/6/17 16:24
 */
public interface RocketMessage extends MqMessage {

	Message buildMessage(RocketSendEvent rocketSendEvent);

}
