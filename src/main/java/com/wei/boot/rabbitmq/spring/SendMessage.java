package com.wei.boot.rabbitmq.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amqp")
public class SendMessage {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@GetMapping("/send")
	public String send(String message) {
		System.out.println("sending msg [ "+message+" ] ...");
		amqpTemplate.convertAndSend("weisihua-spring", message);
		return message + "sent";
	}
	
	@GetMapping("/sendTopic")
	public String sendTopic() {
		//System.out.println("sending msg [ "+message+" ] ...");
		amqpTemplate.convertAndSend("spring-boot-exchange", "weisihua.spring.test", "就是想发送一个topic试试看");
		return "sent";
	}
	
	@GetMapping("/sendFanOut")
	public String sendFanOut() {
		amqpTemplate.convertAndSend("spring-boot-fanout-exchange", null, "就是想发送一个topic试试看");
		return "fanout sent";
	}
}
