package com.itmasir.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用缓冲区完成文件的复制
 */
public class NIOFileChannel003 {


    public static void main(String[] args) {

        File sourceFile = new File("./file01.txt");
        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream outputStream = new FileOutputStream("./file02.txt")) {
            //两个通道
            FileChannel inputStreamChannel = inputStream.getChannel();
            FileChannel outputStreamChannel = outputStream.getChannel();

            inputStreamChannel.transferTo(0, inputStreamChannel.size(), outputStreamChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
