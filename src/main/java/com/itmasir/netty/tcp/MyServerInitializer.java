package com.itmasir.netty.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		System.out.println("初始化");
		ChannelPipeline pipeline = socketChannel.pipeline();
		pipeline.addLast(new MyServerHandler());
	}
}
