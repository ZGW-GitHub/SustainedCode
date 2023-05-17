package com.code.framework.mq.core.event;

import com.code.framework.common.event.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/7/6 17:31
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractMqEvent<M> extends AbstractEvent<M> {

	protected String topic;

}

