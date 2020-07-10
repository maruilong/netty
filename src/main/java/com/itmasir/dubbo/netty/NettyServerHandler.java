package com.itmasir.dubbo.netty;

import com.itmasir.dubbo.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//获取客户端发送的消息 并调用我们的服务
		System.out.println(msg);
		//制定协议 每个消息都以一个字符开头
		if (msg.toString().startsWith("HelloService#hello")) {
			String hello = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#")));
			ctx.writeAndFlush(hello);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
