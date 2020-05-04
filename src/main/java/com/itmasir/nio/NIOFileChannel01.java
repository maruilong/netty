package com.itmasir.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过NIO吧字符写入文件里面
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception {
        String str = "hello world";

        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./file01.txt");
        //真实类型是fileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();

        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        //放入之后 反转buffer
        byteBuffer.flip();

        channel.write(byteBuffer);
        fileOutputStream.flush();
        fileOutputStream.close();

    }
}
