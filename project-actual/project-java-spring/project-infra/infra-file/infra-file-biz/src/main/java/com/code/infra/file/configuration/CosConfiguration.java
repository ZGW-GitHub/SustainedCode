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

package com.code.infra.file.configuration;

import com.code.infra.file.config.CosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Snow
 * @date 2023/6/15 18:18
 */
@Slf4j
@Configuration
public class CosConfiguration {

	@Resource
	private CosConfig cosConfig;

	@Bean
	public TransferManager createTransferManager() {
		// 创建一个 COSClient ，这是访问 COS 服务的基础实例
		COSClient cosClient = createCOSClient();

		ExecutorService threadPool = Executors.newFixedThreadPool(32);

		// 传入一个 ThreadPool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池
		TransferManager transferManager = new TransferManager(cosClient, threadPool);

		// 设置高级接口的配置项
		TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
		// 分块上传阈值和分块大小分别为 5MB 和 1MB
		transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
		transferManagerConfiguration.setMinimumUploadPartSize(1024 * 1024);
		transferManager.setConfiguration(transferManagerConfiguration);

		return transferManager;
	}

	private COSClient createCOSClient() {
		// 1、创建证书
		COSCredentials cosCredentials = new BasicCOSCredentials(cosConfig.getAccessKey(), cosConfig.getSecretKey());

		// 2、创建并配置 ClientConfig（ ClientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分 ）
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setRegion(new Region(cosConfig.getRegion()));

		// 3、创建 COSClient
		return new COSClient(cosCredentials, clientConfig);
	}

}
