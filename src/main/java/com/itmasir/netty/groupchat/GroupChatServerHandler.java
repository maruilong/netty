package com.itmasir.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class GroupChatServerHandler extends SimpleChannelInboundHandler {

    /**
     * 全局事件处理器 声明我们执行的是个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 第一个被访问的方法
     * 表示连接建立
     * 将当前channel加入到组内
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线的客户
        channelGroup.writeAndFlush("[客户]:" + channel.remoteAddress() + "加入聊天");
        channelGroup.add(channel);
    }

    /**
     * 用戶上线
     * 表示channel处于活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户]:" + ctx.channel().remoteAddress() + "上线!");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户]:" + ctx.channel().remoteAddress() + "离线!");

    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线的客户
        channelGroup.writeAndFlush("[客户]:" + channel.remoteAddress() + "离开聊天");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        //完成消息的转发

        Channel channel = channelHandlerContext.channel();

        Iterator<Channel> iterator = channelGroup.iterator();


        for (Channel onChannel : channelGroup) {

            if (channel.equals(onChannel)) {
                onChannel.writeAndFlush(sdf.format(new Date()) + ":[自己]" + o);
            } else {
                onChannel.writeAndFlush(sdf.format(new Date()) + ":" + channel.remoteAddress() + o);

            }


        }


    }
}
