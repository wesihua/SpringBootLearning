package com.wei.boot.rabbitmq.spring;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

	private static final String queueName = "weisihua-spring";
	
	private static final String queueName2 = "weisihua-spring2";
	
	private static final String topicExchange = "spring-boot-exchange";
	
	private static final String fanOutExchange = "spring-boot-fanout-exchange";
	
	@Bean
	public Queue queue() {
		return new Queue(queueName);
	}
	
	@Bean
	public Queue queue2() {
		return new Queue(queueName2);
	}
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(topicExchange);
	}
	
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(fanOutExchange);
	}
	
	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("weisihua.spring.*");
	}
	
	@Bean
	public Binding fanOutBinding(Queue queue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue).to(fanoutExchange);
	}
	
	@Bean
	public Binding fanOutBinding2(Queue queue2, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue2).to(fanoutExchange);
	}
	
	/*
	@Bean
	public Binding binding2(Queue queue2, TopicExchange exchange) {
		return BindingBuilder.bind(queue2).to(exchange).with("weisihua.spring.*");
	}
	
	@Bean
	public MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "process");
	}
	
	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}
	*/
}
