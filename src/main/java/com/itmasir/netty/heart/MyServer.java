package com.itmasir.netty.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 主要用来演示心跳
 */
public class MyServer {

	public static void main(String[] args) {

		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline pipeline = socketChannel.pipeline();
							//加入netty提供的idleStateHandler
							//作用是处理空闲状态的处理器
							//1:多长时间没有读 就会发送一个心跳检测报告 检测是否还是连接的状态
							//2:多久没有写
							//3:全部
							//当idle事件触发以后 会传递给管道的下一个handler的userEventTiggerd处理 (可能是read write read-write)
							pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
							//加入一个进一步空闲检测的handle
							pipeline.addLast(new MyServerHeartHandler());
						}
					});

			ChannelFuture sync = serverBootstrap.bind(9000).sync();
			sync.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

}
