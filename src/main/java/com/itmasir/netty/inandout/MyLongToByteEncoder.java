package com.itmasir.netty.inandout;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
		System.out.println("MyLongToByteEncoder encode 被调用!");
		System.out.println(aLong);
		byteBuf.writeByte(aLong.byteValue());
	}
}
