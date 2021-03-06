package com.itmasir.netty.inandout;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		System.out.println("初始化");
		//获取到pipeline
		ChannelPipeline pipeline = socketChannel.pipeline();
		pipeline.addLast(new MyByteToLongDecoder());
		//入站的handler 解码
		pipeline.addLast(new MyServerHandler());
		//出站的handler 编码
	}
}
