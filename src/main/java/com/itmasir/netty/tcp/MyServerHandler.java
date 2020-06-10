package com.itmasir.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
	private int count;

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
		ChannelPipeline pipeline = channelHandlerContext.pipeline();
		//先把msg转成字节数组
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		String message = new String(bytes, Charset.forName("UTF-8"));
		System.out.println("接收到数据:" + message);

		System.out.println("收到的数据量=" + (++this.count));
		//服务器回送数据给客户端一个随机ID
		String uuid = UUID.randomUUID().toString();
		ByteBuf response = Unpooled.copiedBuffer(uuid.getBytes());
		pipeline.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		super.exceptionCaught(ctx, cause);
	}
}
