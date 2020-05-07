package com.itmasir.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {


	private final int port = 9000;
	private final String host = "127.0.0.1";

	public GroupChatClient() {

	}

	public void run() {


		NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(eventLoopGroup)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						ChannelPipeline pipeline = socketChannel.pipeline();
						//加入相关的handler
						pipeline.addLast("decoder", new StringDecoder());
						pipeline.addLast("encoder", new StringEncoder());
						pipeline.addLast("myHandler", new GroupChatClientHandler());
					}
				});
		try {
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			//得到channel
			Channel channel = channelFuture.channel();
			System.out.println("------" + channel.localAddress() + "已经准备好了");

			//客户端需要输入信息
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				channel.writeAndFlush(nextLine);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}

	}


	public static void main(String[] args) {


		new GroupChatClient().run();

	}


}
