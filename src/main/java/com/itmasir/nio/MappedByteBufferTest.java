package com.itmasir.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 1:MappedByteBuffer可以让文件直接在内存(堆外)中修改,操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("./file01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1 使用读写模式
         * 参数2 可以修改的起始位置
         * 参数3 映射到内存的大小,即将txt的多少个字节映射到内存
         * 可以直接修改的范围 0-5 包左不包右
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'm');
        mappedByteBuffer.put(1, (byte) 'r');
        mappedByteBuffer.put(2, (byte) 'l');

        randomAccessFile.close();
    }
}
