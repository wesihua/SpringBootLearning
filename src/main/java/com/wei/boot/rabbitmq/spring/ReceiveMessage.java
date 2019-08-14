package com.wei.boot.rabbitmq.spring;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessage {

	@RabbitListener(queues="weisihua-spring")
	public void process1(String message) {
		System.out.println("consumer1收到的信息---：" + message);
	}
	
	@RabbitListener(queues="weisihua-spring")
	public void process2(String message) {
		System.out.println("consumer2收到的信息---：" + message);
	}
	
	@RabbitListener(queues="weisihua-spring2")
	public void process3(String message) {
		System.out.println("另外consumer收到的信息---：" + message);
	}
	
	@RabbitListener(queues="weisihua-spring2")
	public void process4(String message) {
		System.out.println("另外xxx consumer收到的信息---：" + message);
	}
}
