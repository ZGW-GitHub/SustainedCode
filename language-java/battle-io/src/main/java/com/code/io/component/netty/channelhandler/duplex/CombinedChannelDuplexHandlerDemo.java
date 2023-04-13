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

package com.code.io.component.netty.channelhandler.duplex;

import com.code.io.component.netty.channelhandler.in.ChannelInboundHandlerDemo;
import com.code.io.component.netty.channelhandler.out.ChannelOutboundHandlerDemo;
import io.netty.channel.CombinedChannelDuplexHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2022/9/9 17:07
 */
@Slf4j
public class CombinedChannelDuplexHandlerDemo extends CombinedChannelDuplexHandler<ChannelInboundHandlerDemo, ChannelOutboundHandlerDemo> {
	

}
