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
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author Snow
 * @date 2021/5/22 18:34
 */
@Activate
public class DemoExporterListener implements ExporterListener {
	@Override
	public void exported(Exporter<?> exporter) throws RpcException {
		System.err.println("DemoExporterListener-exported : " + exporter.getInvoker().getUrl());
	}

	@Override
	public void unexported(Exporter<?> exporter) {
		System.err.println("DemoExporterListener-unexported : " + exporter.getInvoker().getUrl());
	}
}
