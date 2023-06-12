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

package com.code.framework.basic.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Snow
 * @date 2023/6/12 17:25
 */
@Slf4j
public class FileUtil {

	public static String getFileType(String fileName, boolean isJoin) {
		try {
			String fileType = StrUtil.subAfter(fileName, ".", true);

			return isJoin ? StrUtil.DOT + fileType : fileType;
		} catch (Exception e) {
			log.error("获取文件类型出错，fileName : {}", fileName, e);
			throw e;
		}
	}

}
