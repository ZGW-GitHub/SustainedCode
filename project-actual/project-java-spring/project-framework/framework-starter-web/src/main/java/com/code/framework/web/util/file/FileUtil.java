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

package com.code.framework.web.util.file;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Snow
 * @date 2023/6/12 17:23
 */
@Slf4j
public class FileUtil {

	public static File convert(MultipartFile multipartFile) {
		// 获取文件名
		String originalFilename = multipartFile.getOriginalFilename();
		if (StrUtil.isBlank(originalFilename)) {
			throw new RuntimeException();
		}

		// 获取默认定位到的当前用户目录("user.dir"),也就是当前应用的根路径
		String tempDir = System.getProperty("user.dir");

		// 获取文件名
		String filename = StrUtil.subBefore(originalFilename, ".", true);
		// 获取文件后缀
		String fileExt = StrUtil.subAfter(originalFilename, ".", true);

		// 在项目根路径生成临时文件
		File tempFile;
		try {
			tempFile = File.createTempFile(filename, "." + fileExt, new File(tempDir));

			multipartFile.transferTo(tempFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		tempFile.deleteOnExit();

		return tempFile;
	}

}
