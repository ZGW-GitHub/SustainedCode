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

package com.code.service.file.configuration;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
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

	@Bean
	public TransferManager createTransferManager() {
		// 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
		COSClient cosClient = createCOSClient();

		ExecutorService threadPool = Executors.newFixedThreadPool(32);

		// 传入一个 ThreadPool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
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
		// 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
		String tmpSecretId = "SECRET_ID";
		String tmpSecretKey = "SECRETKEY";
		String sessionToken = "TOKEN";
		BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);

		// 2 设置 bucket 的地域
		Region region = new Region("COS_REGION"); // COS_REGION 参数：配置存储桶的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224

		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
		ClientConfig clientConfig = new ClientConfig(region);

		// 3 生成 cos 客户端
		return new COSClient(cred, clientConfig);
	}

}
