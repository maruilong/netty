package com.itmasir.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阻塞(blocking)IO 多少个客户端 就有多少个线程
 */
public class BIOServer {


    public static void main(String[] args) throws Exception {

        //使用线程池机制完成一个BIO的服务端


        //1 创建一个线程池
        //2 如果有客户端连接 就创建一个线程 与之通讯
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();


        final ServerSocket serverSocket = new ServerSocket(6000);

        System.out.println("服务器启动了");

        while (true) {
            //监听 等待客户端连接
            final Socket accept = serverSocket.accept();

            System.out.println("有客户端连接");

            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    handler(accept);
                }
            });
        }
    }

    //编写一个handler方法 可以和客户端通信
    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        //获取输入流
        try {
            System.out.println("线程信息:" + Thread.currentThread().getId());
            InputStream inputStream = socket.getInputStream();
            //循环的读取客户端发来的数据
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    //输出客户端发送的数据
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("关闭client的连接");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
