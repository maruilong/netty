package com.itmasir.netty.inandout;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();

		//1:加入一个自定义的handler 处理业务
		//2:加入一个出战的handler 对数据进行编码
		pipeline.addLast(new MyLongToByteEncoder());
		pipeline.addLast(new MyClientHandler());
	}
}
