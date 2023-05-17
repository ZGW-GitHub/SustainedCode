package com.code.framework.mq.core.client;

import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @author 愆凡
 * @date 2022/6/16 14:56
 */
public interface MqClientBuilder<T> extends SmartInitializingSingleton {

	/**
	 * 构建客户端
	 */
	void builderClient();

	/**
	 * 返回客户端
	 *
	 * @return 客户端
	 */
	T client();

}
