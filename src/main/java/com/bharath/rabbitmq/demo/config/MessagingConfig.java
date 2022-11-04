package com.bharath.rabbitmq.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String CUSTOMER_QUEUE = "customer_queue";
    public static final String ORDER_QUEUE = "order_queue";
    public static final String ORDER_TOPIC = "order_topic";
    //public static final String ROUTING_KEY = "order_routingKey";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE);
    }

    @Bean
    public Queue consumerQueue() {
        return new Queue(CUSTOMER_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ORDER_TOPIC);
    }

    @Bean
    public Binding orderBinding(TopicExchange exchange) {
        return BindingBuilder.bind(orderQueue())
                .to(exchange)
                .with(orderQueue().getName());
    }

    @Bean
    public Binding consumerBinding(TopicExchange exchange) {
        return BindingBuilder.bind(consumerQueue())
                .to(exchange)
                .with(consumerQueue().getName());
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
