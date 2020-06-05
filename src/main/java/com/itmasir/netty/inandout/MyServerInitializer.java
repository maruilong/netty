package com.itmasir.netty.inandout;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		//获取到pipeline
		ChannelPipeline pipeline = socketChannel.pipeline();

		//入站的handler 解码
		pipeline.addLast(null);

		//出站的handler 编码


	}
}
