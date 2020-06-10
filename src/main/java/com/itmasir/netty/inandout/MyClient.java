package com.itmasir.netty.inandout;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {

	private static final int port = 8888;
	private static final String host = "127.0.0.1";

	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					.handler(new MyClientInitializer());

			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
		} catch (Exception e) {
			group.shutdownGracefully();
		}
	}
}
