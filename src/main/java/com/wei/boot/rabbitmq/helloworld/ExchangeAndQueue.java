package com.wei.boot.rabbitmq.helloworld;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class ExchangeAndQueue {

	public static void main(String[] args) {
		HelloWorld hw = new HelloWorld();
		Connection connection = hw.getConnection();
		try {
			String message = "转换器和队列小样例-一会就消失";
			Map<String, Object> map = new HashMap<>();
			map.put("weisihua", "weisihua");
			map.put("date", new Date());
			
			Channel channel = connection.createChannel();
			channel.exchangeDeclare("exchange_test", "direct", true);
			channel.queueDeclare("queue_test", false, false, false, map);
			channel.queueBind("queue_test", "exchange_test", "exchange_queue_routing");
			channel.basicPublish("exchange_test", "exchange_queue_routing", 
					new AMQP.BasicProperties.Builder()
					.headers(map)
					//.expiration("30000")
					.userId("weisihua")
					.build(), message.getBytes());
			//channel.basicPublish("exchange_test", "exchange_queue_routing", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println("信息push成功。。。");
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
}
