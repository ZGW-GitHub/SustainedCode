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

package com.code.dubbo.listener;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.InvokerListener;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author Snow
 * @date 2021/5/22 18:23
 */
@Activate(order = -1000)
public class DemoInvokerListener implements InvokerListener {
    @Override
    public void referred(Invoker<?> invoker) throws RpcException {
        System.err.println("DemoInvokerListener-referred : " + invoker.getUrl());
    }

    @Override
    public void destroyed(Invoker<?> invoker) {
        System.err.println("DemoInvokerListener-destroyed : " + invoker.getUrl());
    }
}
