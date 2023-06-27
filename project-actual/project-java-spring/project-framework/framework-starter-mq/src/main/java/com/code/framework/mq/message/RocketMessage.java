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

package com.code.framework.mq.message;

import com.code.framework.mq.core.event.RocketSendEvent;
import org.apache.rocketmq.common.message.Message;

/**
 * @author 愆凡
 * @date 2022/6/17 16:24
 */
public interface RocketMessage extends MqMessage {

	Message buildMessage(RocketSendEvent rocketSendEvent);

}
