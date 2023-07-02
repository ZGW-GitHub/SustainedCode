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

package com.code.infra.file.framework.component;

import com.code.infra.file.common.util.UploadUtil;
import com.code.infra.file.framework.config.CosConfig;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.transfer.TransferManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author Snow
 * @date 2023/6/15 18:11
 */
@Slf4j
@Component
public class CosClient {

	@Resource
	private TransferManager transferManager;

	@Resource
	private CosConfig cosConfig;

	public void upload(List<MultipartFile> files) {
		for (MultipartFile file : files) {
			upload(file);
		}
	}

	public void upload(File file) {
		String fileKey = UploadUtil.generateKey();

		try {
			transferManager.upload(cosConfig.getBucketName(), fileKey, file);
		} catch (Exception e) {
			log.error("【 腾讯云文件上传 】file: {}, 发生异常: {}", file.getName(), e.getMessage(), e);
		}
	}

	public void upload(MultipartFile file) {
		String fileKey = UploadUtil.generateKey();

		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());

			transferManager.upload(cosConfig.getBucketName(), fileKey, file.getInputStream(), objectMetadata);
		} catch (Exception e) {
			log.error("【 腾讯云文件上传 】file: {}, 发生异常: {}", file.getName(), e.getMessage(), e);
		}
	}

}
