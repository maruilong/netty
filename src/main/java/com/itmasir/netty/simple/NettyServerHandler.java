package com.itmasir.netty.simple;

import com.itmasir.codec.StudentPOJO;
import com.itmasir.codec2.MyDataInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * 我们自定义一个Handler 需要继承netty规定好的某个HandlerAdapter
 * 这是我们自定义的一个Handler 才能成为一个handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    /**
     * 读取数据的事件(可以读取客户端发送的事件)
     * ChannelHandlerContext 上下文对象 含有管道pipeline 通道 Channel
     *
     * @param ctx
     * @param message
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage message) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = message.getDataType();

        if (MyDataInfo.MyMessage.DataType.studentType.equals(dataType)) {
            MyDataInfo.Student student = message.getStudent();
            System.out.println(student.getId() + "学生:" + student.getName());
        } else if (MyDataInfo.MyMessage.DataType.Worker.equals(dataType)) {
            MyDataInfo.Worker worker = message.getWorker();
            System.out.println(worker.getAge() + "工人:" + worker.getName());
        } else {
            System.out.println("数据不对");
        }
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
