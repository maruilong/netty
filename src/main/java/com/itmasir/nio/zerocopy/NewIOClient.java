package com.itmasir.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7000);

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(inetSocketAddress);

        String fileName = "./file01.txt";
        FileInputStream inputStream = new FileInputStream(fileName);
        FileChannel fileChannel = inputStream.getChannel();
        long startTime = System.currentTimeMillis();
        //在linux下可以直接发送完成
        //在windows一次只能发送8m文件
        //底层使用了0拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println(System.currentTimeMillis() - startTime);
        fileChannel.close();
    }
}
