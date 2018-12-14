package com.wei.boot.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class HelloWorld {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
//		factory.setHost("47.97.100.29");
//		factory.setVirtualHost("/");
//		factory.setPort(5672);
//		factory.setUsername("weisihua");
//		factory.setPassword("weisihua");
		
		factory.setUri("amqp://weisihua:weisihua@47.97.100.29:5672");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("helloworld", false, false, false, null);
		String message = "hello world";
		channel.basicPublish("", "helloworld", null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		
		channel.close();
		connection.close();
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUri("amqp://weisihua:weisihua@47.97.100.29:5672");
			connection = factory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
