package com.itmasir.dubbo.provider;

import com.itmasir.dubbo.publicI.HelloService;

public class HelloServiceImpl implements HelloService {

	/**
	 * 当有消费方调用该方法时 返回结果
	 *
	 * @param msg
	 * @return
	 */
	@Override
	public String hello(String msg) {
		System.out.println("收到客户端消息!");
		if (msg != null) {
			return "收到消息:" + msg;
		} else {
			return "没有收到消息";
		}

	}
}
