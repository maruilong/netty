package com.itmasir.nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 使用NIO完成0拷贝
 */
public class NewIOServer {

    public static void main(String[] args) throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7000);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(inetSocketAddress);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        SocketChannel socketChannel = serverSocketChannel.accept();

        int readCount = 0;
        while (-1 != readCount) {
            try {
                readCount = socketChannel.read(byteBuffer);
            } catch (Exception e) {
                break;
            }
            //倒带
            byteBuffer.rewind();
        }
    }
}
