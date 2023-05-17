package com.code.framework.common.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/16 11:37
 */
@Slf4j
@AllArgsConstructor
public enum EventTypeEnum {

	/**
	 * Kafka
	 */
	SEND_KAFKA("发送消息到 kafka"),

	/**
	 * RocketMq
	 */
	SEND_ROCKETMQ("发送消息到 rocketmq");

	/**
	 * 说明
	 */
	private final String description;

}
