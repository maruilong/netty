package com.itmasir.longio.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
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
							pipeline.addLast(new HttpServerCodec());
							//因为是以块写入系统的 所以需要添加ChunkedWriteHandler
							pipeline.addLast(new ChunkedWriteHandler());
							//因为http协议在传输中士分段的HttpObjectAggregator 可以将多个段聚合起来
							pipeline.addLast(new HttpObjectAggregator(8192));
							//对于websocket的数据 是以帧的形式传递的
							//可以看到webSocketFrame下面有六个子类
							//浏览器请求时候:ws://localhost:9000/hello
							pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
							pipeline.addLast(new MyTextWebSocketFrameHandler());
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
