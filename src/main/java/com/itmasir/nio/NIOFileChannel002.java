package com.itmasir.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用Channel和buffer读取文件中的内容
 */
public class NIOFileChannel002 {


    public static void main(String[] args) {

        try (FileInputStream inputStream = new FileInputStream("./file01.txt")) {
            FileChannel channel = inputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
