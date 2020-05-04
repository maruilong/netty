package com.itmasir.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用NIO完成一个服务端
 */
public class NIOServer {

    public static void main(String[] args) throws Exception {

        //ServerSocketChannel ->>ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //等到一个Selector对象
        Selector selector = Selector.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 6000));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //吧 serverSocketChannel 注册到 selector 关系
        //当serverSocketChannel进行OP_ACCEPT操作的时候 进入selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("等待了一秒 没有事件");
                continue;
            }
            //如果返回的不是0 就获取到相关的selectionKeys集合
            //关注事件的集合 可以通过事件获得通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey selectionKey = keyIterator.next();
                //如果是客户端连接事件
                if (selectionKey.isAcceptable()) {
                    //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //将当前的通道注册到选择器上 关注事件为OP_READ 同时给channel关联一个buffer
                    SelectionKey acceptKey = socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                if (selectionKey.isReadable()) {
                    //通过key 反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
                    System.out.println("客户端发送的:" + new String(byteBuffer.array()));
                    //手动从当前集合中删除key 防止重复操作
                }
                keyIterator.remove();
            }
        }
    }

}
