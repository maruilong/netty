package com.itmasir.netty.inandout;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {

	/**
	 * @param channelHandlerContext 上下围
	 * @param byteBuf               入站的
	 * @param list                  list 解码后的数据交给下一个handler
	 * @throws Exception
	 */
	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		if (byteBuf.readByte() >= 8) {
			list.add(byteBuf.readLong());
		}
	}
}
