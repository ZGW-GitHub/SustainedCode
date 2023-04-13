/*
 * Copyright (C) <2023> <Snow>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.code.rocketmq.msgtype;

import com.alibaba.fastjson.JSON;
import com.code.rocketmq.ConstantPool;
import com.code.rocketmq.RocketMqApplicationTest;
import com.code.rocketmq.utils.MsgGenerateUtil;
import com.code.rocketmq.utils.ProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;

/**
 * @author Snow
 * @date 2021/12/21 16:45
 */
@Slf4j
public class TransactionalTest extends RocketMqApplicationTest {

	@Test
	void producerTest() throws MQClientException {
		// 1、启动 Producer 客户端
		TransactionMQProducer producer = ProducerUtil.startProducer(new MyTransactionListener());

		// 2、发送消息
		SendResult sendResult = producer.sendMessageInTransaction(MsgGenerateUtil.makeMsg(ConstantPool.DEFAULT_TOPIC), new Object());

		System.err.println(JSON.toJSONString(sendResult));
	}

	static class MyTransactionListener implements TransactionListener {
		/**
		 * 事务消息发送成功后，该方法会被调用以执行 Producer 的本地事务
		 *
		 * @param msg 监听到的事务消息
		 * @param arg 事务消息携带的额外参数
		 *
		 * @return 本地事务的执行结果：rollback 、commit 、unknown
		 */
		@Override
		public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
			System.err.println("执行本地事务，事务消息：" + msg + " arg：" + arg);

			return LocalTransactionState.UNKNOW;
		}

		/**
		 * 回查本地事务，实现方式：<br/>
		 * {@link #executeLocalTransaction(Message, Object)} 方法在执行本地事务前可以记录 msg 的事务编号、事务状态(unknown) 存储到数据库<br/>
		 * 当本地事务执行成功，将数据库中的事务状态更新为：commit<br/>
		 * 当本地事务执行失败，将数据库中的事务状态更新为：rollback
		 *
		 * @param msg 事务消息
		 *
		 * @return 回查结果：rollback 、commit 、unknown
		 */
		@Override
		public LocalTransactionState checkLocalTransaction(MessageExt msg) {
			System.err.println("回查消息：" + msg);

			// 查询本地事务的执行结果，这里没有实现，先直接返回 commit
			return LocalTransactionState.COMMIT_MESSAGE;
		}
	}

}
