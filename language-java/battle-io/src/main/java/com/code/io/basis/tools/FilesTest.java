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

package com.code.io.basis.tools;

import com.code.io.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Snow
 * @date 2022/8/3 15:12
 */
@Slf4j
public class FilesTest {

    /**
     * 创建目录
     */
    @Test
    void createDir() throws IOException {
        Path path = Paths.get(CommonUtil.DEFAULT_DIR_PATH);

        System.out.println(path);
        assertTrue(path.toFile().exists());

        // 创建子目录：/test1/test2
        path = Paths.get(CommonUtil.DEFAULT_DIR_PATH + "/test1/test2");
        System.out.println(path);
        assertFalse(path.toFile().exists());

        path = Files.createDirectories(path);
        assertTrue(path.toFile().exists());
    }

    /**
     * 创建临时目录
     */
    @Test
    void createTempDir() throws IOException {
        Path path = Paths.get(CommonUtil.DEFAULT_DIR_PATH);

        // 创建临时目录：/temp-xxx
        Path tempPath = Files.createTempDirectory(path, "temp-"); // 注意：父目录不存在会报错
        assertTrue(tempPath.toFile().exists());
    }

    /**
     * 删除目录
     */
    @Test
    void deleteDir() throws IOException {
        createDir();

        Path path = Paths.get(CommonUtil.DEFAULT_DIR_PATH + "/test1/test2");
        Path parentPath = path.getParent();
        System.out.println(parentPath);

        assertTrue(path.toFile().delete());
        assertTrue(parentPath.toFile().delete());
    }

    /**
     * 创建文件
     */
    @Test
    void createFile() throws IOException {
        Path dirPath = Paths.get(CommonUtil.DEFAULT_DIR_PATH + "/test1/test2");
        Path filePath = Paths.get(dirPath + "/" + CommonUtil.DEFAULT_FILE_NAME);

        assertFalse(dirPath.toFile().exists());
        assertFalse(filePath.toFile().exists());

        Files.createDirectories(dirPath);
        Files.createFile(filePath);

        assertTrue(dirPath.toFile().exists());
        assertTrue(filePath.toFile().exists());
    }

}
