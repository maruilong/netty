package com.itmasir.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws Exception {
        //创建bossGroup 只处理连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        //创建workerGroup 真正与客户端的业务处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //正式运行的两个都是无线循环

        //创建服务器启动的配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            //链式编程进行设置
            serverBootstrap
                    .group(bossGroup, workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //设置通道使用NioSocketChannel
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyServerHandler());//给管道的最后增加一个处理器
                        }
                    }); //给workerGroup的EventLoop设置管道设置处理器

            System.out.println("....服务器准备好了");

            //绑定一个端口 并且同步 生撑一个ChannelFuture 对象
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
