package com.itmasir.dubbo.provider;

import com.itmasir.dubbo.netty.NettyServer;

/**
 * 服务提供者
 */
public class ServerBootStrap {

	public static void main(String[] args) {
		NettyServer.startServer("127.0.0.1", 8888);
	}


}
