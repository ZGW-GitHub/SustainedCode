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

package com.code.io;

import io.netty.buffer.ByteBuf;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Snow
 * @date 2022/8/2 14:25
 */
@Slf4j
public class CommonUtil {

    public static final String DEFAULT_FILE_NAME = "temp.txt";

    public static final String DEFAULT_DIR_PATH = getPath();

    public static final String DEFAULT_FILE_PATH = getFilePath();

    public static final String SERVER_HOST = "127.0.0.1";

    public static final Integer SERVER_PORT = 65001;

    public static final String MSG_CLOSE = "close";


    /**
     * 获取文件夹路径
     *
     * @return 默认的文件夹路径
     */
    private static String getPath() {
        URL resource = CommonUtil.class.getClassLoader().getResource("");
        assertNotNull(resource);

        return resource.getPath().replace("target/classes", "src/main/resources");
    }

    /**
     * 获取默认文件的路径
     *
     * @return 默认文件的路径
     */
    private static String getFilePath() {
        return getFilePath(DEFAULT_FILE_NAME);
    }

    /**
     * 获取文件路径
     *
     * @return 给定文件的文件路径
     */
    @SneakyThrows
    public static String getFilePath(String fileName) {
        String filePath = getPath() + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            assertTrue(file.createNewFile());
        }
        return filePath;
    }

    /**
     * 打印 ByteBuffer 的信息
     *
     * @param buffer {@link ByteBuffer}
     */
    public static void printBufferInfo(ByteBuffer buffer) {
        printBufferInfo(buffer, null);
    }

    /**
     * 打印 ByteBuffer 的信息
     *
     * @param buffer {@link ByteBuffer}
     * @param msg    说明
     */
    @SneakyThrows
    public static void printBufferInfo(ByteBuffer buffer, String msg) {
        if (msg == null) {
            System.err.printf("[ limit : %s ] [ position : %s ]\n\n", buffer.limit(), buffer.position());
        } else {
            System.err.printf("%s :\n[ limit : %s ] [ position : %s ]\n\n", msg, buffer.limit(), buffer.position());
        }

        TimeUnit.MILLISECONDS.sleep(200);
    }

    public static void printBufInfo(ByteBuf buffer) {
        printBufInfo(buffer, null);
    }

    @SneakyThrows
    public static void printBufInfo(ByteBuf buffer, String msg) {
        if (msg == null) {
            System.err.printf("[ maxCapacity : %s ] [ capacity : %s ] [ writableBytes : %s ] [ readableBytes : %s ]\n\n"
                    , buffer.maxCapacity(), buffer.capacity(), buffer.writableBytes(), buffer.readableBytes());
        } else {
            System.err.printf("%s :\n[ maxCapacity : %s ] [ capacity : %s ] [ writableBytes : %s ] [ readableBytes : %s ]\n\n"
                    , msg, buffer.maxCapacity(), buffer.capacity(), buffer.writableBytes(), buffer.readableBytes());
        }

        TimeUnit.MILLISECONDS.sleep(200);
    }

    /**
     * 打印 RandomAccessFile 的信息
     *
     * @param file {@link RandomAccessFile}
     */
    public static void printFileInfo(RandomAccessFile file) {
        printFileInfo(file, null);
    }

    /**
     * 打印 RandomAccessFile 的信息
     *
     * @param file {@link RandomAccessFile}
     * @param msg  说明
     */
    @SneakyThrows
    public static void printFileInfo(RandomAccessFile file, String msg) {
        if (msg == null) {
            System.err.printf("[ length : %s ] [ position : %s ]\n\n", file.length(), file.getFilePointer());
        } else {
            System.err.printf("%s :\n[ length : %s ] [ position : %s ]\n\n", msg, file.length(), file.getFilePointer());
        }

        TimeUnit.MILLISECONDS.sleep(200);
    }

    public static void printFileLockInfo(FileLock fileLock) {
        System.err.printf("加锁区域：[ %s - %s ] ，是否是共享锁：%s\n", fileLock.position(), fileLock.position() + fileLock.size(), fileLock.isShared());
    }

    /**
     * 打印 FileChannel 的信息
     *
     * @param fileChannel {@link FileChannel}
     */
    public static void printFileChannelInfo(FileChannel fileChannel) {
        printFileChannelInfo(fileChannel, null);
    }

    /**
     * 打印 FileChannel 的信息
     *
     * @param fileChannel {@link FileChannel}
     * @param msg         说明
     */
    @SneakyThrows
    public static void printFileChannelInfo(FileChannel fileChannel, String msg) {
        if (msg == null) {
            System.err.printf("[ size : %s ] [ position : %s ]\n\n", fileChannel.size(), fileChannel.position());
        } else {
            System.err.printf("%s :\n[ size : %s ] [ position : %s ]\n\n", msg, fileChannel.size(), fileChannel.position());
        }

        TimeUnit.MILLISECONDS.sleep(200);
    }

    @SneakyThrows
    public static void printString(String msg) {
        System.out.println(msg + "\n");

        TimeUnit.MILLISECONDS.sleep(200);
    }

    /**
     * 打印堆外内存使用情况
     */
    @SneakyThrows
    public static void nonHeapInfo() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        System.out.println("\n===================================================");

        ObjectName objectName = new ObjectName("java.nio:type=BufferPool,name=direct");
        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);
        nonHeapInfo(mBeanServer, mBeanInfo, objectName);

        System.out.println();

        objectName = new ObjectName("java.nio:type=BufferPool,name=mapped");
        mBeanInfo = mBeanServer.getMBeanInfo(objectName);
        nonHeapInfo(mBeanServer, mBeanInfo, objectName);

        System.out.println("===================================================\n");
        TimeUnit.MILLISECONDS.sleep(200);
    }

    @SneakyThrows
    private static void nonHeapInfo(MBeanServer mBeanServer, MBeanInfo mBeanInfo, ObjectName objectName) {
        for (MBeanAttributeInfo info : mBeanInfo.getAttributes()) {
            System.out.println(info.getName() + " : " + mBeanServer.getAttribute(objectName, info.getName()));
        }
    }

    public static String currentMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return stackTraceElements[2].getMethodName();
    }

    public static String currentStackInfo() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return stackTraceElements[2].getClassName() + " # " + stackTraceElements[2].getMethodName();
    }

}
