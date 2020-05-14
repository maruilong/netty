package com.itmasir.netty.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHeartHandler extends ChannelInboundHandlerAdapter {

	/**
	 * @param ctx 上下文
	 * @param evt 事件
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//判断事件的类型
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
			switch (idleStateEvent.state()) {
				case ALL_IDLE:
					System.out.println(ctx.channel().remoteAddress() + "全部空闲");
					break;
				case READER_IDLE:
					System.out.println(ctx.channel().remoteAddress() + "读空闲");
					break;
				case WRITER_IDLE:
					System.out.println(ctx.channel().remoteAddress() + "写空闲");
					break;
				default:
					return;
			}
		}
	}
}
