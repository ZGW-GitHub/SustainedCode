package com.code.framework.mq.core.client;

import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @author 愆凡
 * @date 2022/6/16 14:56
 */
public interface MqClientBuilder<T, C> extends SmartInitializingSingleton {

	/**
	 * 构建客户端
	 */
	void builderClient(C c);

	/**
	 * 返回客户端
	 *
	 * @return 客户端
	 */
	T client();

	String clientId();

}
