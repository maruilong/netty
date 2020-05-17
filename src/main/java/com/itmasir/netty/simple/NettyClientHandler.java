package com.itmasir.netty.simple;

import com.itmasir.codec.StudentPOJO;
import com.itmasir.codec2.MyDataInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

import static io.netty.util.CharsetUtil.UTF_8;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 当通道就绪就会触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client " + ctx);

        int random = new Random(0).nextInt();
        MyDataInfo.MyMessage message = null;
        System.out.println(random);
        if (0 == random) {
            MyDataInfo.MyMessage.Builder builder = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.studentType);
            message = builder.setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("shinian").build()).build();
        } else {
            MyDataInfo.MyMessage.Builder builder = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.Worker);
            message = builder.setWorker(MyDataInfo.Worker.newBuilder().setAge(23).setName("dsadasd").build()).build();
        }
        //发送一个Student对象到服务器
        ctx.writeAndFlush(message);
    }

    /**
     * 当通道有读取事件时会触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的消息" + byteBuf.toString((UTF_8)));
        System.out.println("服务器的地址: " + ctx.channel().remoteAddress());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
