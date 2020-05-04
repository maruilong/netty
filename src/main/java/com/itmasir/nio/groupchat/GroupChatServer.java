package com.itmasir.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用NIO完成一个群聊
 */
public class GroupChatServer {

    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private final String HOST = "127.0.0.1";
    private static final int PORT = 6000;

    //初始化工作
    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(HOST, PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    /**
     * 监听
     */
    public void listen() {

        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    //如果count大于0 说明有事件要处理
                    //先获得key的集合
                    Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " 上线");
                        } else if (selectionKey.isReadable()) {
                            //如果是读取事件
                            readData(selectionKey);
                        }
                        selectionKeyIterator.remove();
                    }
                } else {
                    System.out.println("等待");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取客户端消息
     */
    private void readData(SelectionKey selectionKey) {
        SocketChannel channel = null;
        try {
            //定义一个socketChannel
            channel = (SocketChannel) selectionKey.channel();
            //创建缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if (count > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println(msg);
                //向在线的所有客户端转发消息
                sendInfoToOtherClient(msg, channel);
            }
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了...");
                //取消注册
                selectionKey.cancel();
                //关闭注册
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 转发消息给其他的在线客户端(通道)
     * 需要排除自己
     */
    private void sendInfoToOtherClient(String msg, SocketChannel self) {
        System.out.println("服务器转发消息中");
        for (SelectionKey selectionKey : selector.keys()) {
            //取出key对应的channel
            SelectableChannel channel = selectionKey.channel();
            if (self != channel && channel instanceof SocketChannel) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                try {
                    SocketChannel socketChannel = (SocketChannel) channel;
                    socketChannel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
