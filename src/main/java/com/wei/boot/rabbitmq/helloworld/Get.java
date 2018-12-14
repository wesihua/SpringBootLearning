package com.wei.boot.rabbitmq.helloworld;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.GetResponse;

public class Get {

	public static void main(String[] args) throws IOException {
		HelloWorld hw = new HelloWorld();
		Connection connection = hw.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("queue_test", false, false, false, null);
		channel.basicQos(1); // 一次只处理一条信息
		GetResponse response = channel.basicGet("queue_test", false);
		if(null != response) {
			String message = new String(response.getBody(), "utf-8");
			System.out.println(message);
			//AMQP.BasicProperties prop = response.getProps();
			long deliveryTag = response.getEnvelope().getDeliveryTag();
			System.out.println(deliveryTag);
		}
	}
}
