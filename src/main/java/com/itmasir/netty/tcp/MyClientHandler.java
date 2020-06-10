package com.itmasir.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	private int count = 0;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//使用客户端发送十条数据
		for (int i = 0; i < 10; i++) {
			ByteBuf byteBuf = Unpooled.copiedBuffer("hello,server" + i, Charset.forName("UTF-8"));
			ctx.writeAndFlush(byteBuf);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		System.out.println(new String(bytes, Charset.forName("UTF-8")));
		System.out.println(++count);
	}

}
