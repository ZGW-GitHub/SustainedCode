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

package com.code.tcp.nio.basis.options;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.SocketOption;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @author Snow
 * @date 2022/11/5 22:00
 */
@Slf4j
public class DemoTest {

    @Test
    @SneakyThrows
    void demo() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            Set<SocketOption<?>> socketOptions = serverSocketChannel.supportedOptions();

            socketOptions.forEach(option -> {
                System.err.println(option.getClass() + " : " + option.name() + " --- " + option.type());
            });
        }
    }

}
