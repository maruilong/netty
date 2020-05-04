package com.itmasir.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * 我们自定义一个Handler 需要继承netty规定好的某个HandlerAdapter
 * 这是我们自定义的一个Handler 才能成为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 读取数据的事件(可以读取客户端发送的事件)
     * ChannelHandlerContext 上下文对象 含有管道pipeline 通道 Channel
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        //将msg 转成ByteBuf ByteBuf是由Netty提供的 不是NIO的ByteBuffer
        ByteBuf byteBuffer = (ByteBuf) msg;

        System.out.println("客户端发送的消息是:" + byteBuffer.toString(UTF_8));
        System.out.println("客户端地址:" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是 write + flush
        //将数据写入缓存 并刷新
        //一般来讲 我们对这个发送的数据 会进行编码
        ByteBuf buffer = Unpooled.copiedBuffer("hello,客户端!", UTF_8);
        ctx.writeAndFlush(buffer);
        super.channelReadComplete(ctx);
    }
}
