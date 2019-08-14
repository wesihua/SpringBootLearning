package com.wei.boot.rabbitmq.spring;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

	public void process(String message) {
		//System.out.println("接收到消息2："+message);
	}
}
