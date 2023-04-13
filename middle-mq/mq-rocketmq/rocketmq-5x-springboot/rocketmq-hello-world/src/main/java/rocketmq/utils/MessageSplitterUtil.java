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

package rocketmq.utils;

import org.apache.rocketmq.common.message.Message;

import java.util.*;

/**
 * 从官方拷贝过来的，用来按照大小拆分批量消息
 *
 * @author Snow
 * @date 2021/12/14
 */
public class MessageSplitterUtil implements Iterator<List<Message>> {
	private final int           SIZE_LIMIT = 1024 * 1024 * 4;
	private final List<Message> messages;
	private       int           currIndex;

	public MessageSplitterUtil(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public boolean hasNext() {
		return currIndex < messages.size();
	}

	@Override
	public List<Message> next() {
		int startIndex = getStartIndex();
		int nextIndex  = startIndex;
		int totalSize  = 0;
		for (; nextIndex < messages.size(); nextIndex++) {
			Message message = messages.get(nextIndex);
			int     tmpSize = calcMessageSize(message);
			if (tmpSize + totalSize > SIZE_LIMIT) {
				break;
			} else {
				totalSize += tmpSize;
			}
		}
		List<Message> subList = messages.subList(startIndex, nextIndex);
		currIndex = nextIndex;
		return subList;
	}

	private int getStartIndex() {
		Message currMessage = messages.get(currIndex);
		int     tmpSize     = calcMessageSize(currMessage);
		while (tmpSize > SIZE_LIMIT) {
			currIndex += 1;
			Message message = messages.get(currIndex);
			tmpSize = calcMessageSize(message);
		}
		return currIndex;
	}

	private int calcMessageSize(Message message) {
		int                 tmpSize    = message.getTopic().length() + message.getBody().length;
		Map<String, String> properties = Optional.ofNullable(message.getProperties()).orElse(new HashMap<>(0));
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			tmpSize += entry.getKey().length() + entry.getValue().length();
		}
		tmpSize = tmpSize + 20; // 增加⽇日志的开销20字节
		return tmpSize;
	}

}
