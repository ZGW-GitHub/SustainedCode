package com.code.framework.basic.event;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 愆凡
 * @date 2022/6/17 11:18
 */
@Slf4j
@Data
public abstract class AbstractEvent<T> {

	protected T source;

}
