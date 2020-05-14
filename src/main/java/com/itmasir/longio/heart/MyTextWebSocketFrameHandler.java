package com.itmasir.longio.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
		System.out.println("服务器收到消息:" + textWebSocketFrame.text());
		//回复
		channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("服务器时间:" +
				LocalDateTime.now() + " " + textWebSocketFrame.text()));

	}

	/**
	 * 当外部链接吼 就会触发
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//id表示一个唯一的一个值
		System.out.println(ctx.channel().id().asLongText());
		System.out.println(ctx.channel().id().asShortText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

		System.out.println("channel被取消了");
	}
}
