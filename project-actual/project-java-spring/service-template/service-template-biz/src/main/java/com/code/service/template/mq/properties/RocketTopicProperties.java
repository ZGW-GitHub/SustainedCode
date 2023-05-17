package com.code.service.template.mq.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 愆凡
 * @date 2022/6/16 23:41
 */
@Slf4j
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "mq.rocketmq.topic")
public class RocketTopicProperties {

	private String testTopic;

}
