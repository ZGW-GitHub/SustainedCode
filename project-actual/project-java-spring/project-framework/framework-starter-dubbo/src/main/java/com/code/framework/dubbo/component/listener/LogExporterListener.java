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

package com.code.framework.dubbo.component.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author Snow
 * @date 2021/5/22 18:34
 */
@Slf4j
@Activate
public class LogExporterListener implements ExporterListener {

	@Override
	public void exported(Exporter<?> exporter) throws RpcException {
		log.debug("【 Dubbo LogExporterListener 】exported : {}", exporter.getInvoker().getUrl());
	}

	@Override
	public void unexported(Exporter<?> exporter) {
		log.debug("【 Dubbo LogExporterListener 】unexported : {}", exporter.getInvoker().getUrl());
	}

}
