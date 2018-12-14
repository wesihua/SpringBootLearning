package com.wei.boot.rabbitmq.helloworld;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Receive {

	public static void main(String[] args) throws Exception {
		HelloWorld hw = new HelloWorld();
		Connection connection = hw.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("queue_test", false, false, false, null);
		channel.basicQos(1); // 一次只处理一条信息
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "utf-8");
				System.out.println(consumerTag);
				System.out.println("接收到信息："+message);
			}
		};
		channel.basicConsume("queue_test", true, consumer);
		
		/*
		DeliverCallback callback = new DeliverCallback() {
			@Override
			public void handle(String consumerTag, Delivery message) throws IOException {
				String msg = new String(message.getBody(), "utf-8");
				System.out.println(consumerTag);
				System.out.println("接收到信息："+msg);
			}
		};
		channel.basicConsume("queue_test", true, callback, consumerTag -> {});
		*/
		
		channel.close();
		connection.close();
	}
	
}
