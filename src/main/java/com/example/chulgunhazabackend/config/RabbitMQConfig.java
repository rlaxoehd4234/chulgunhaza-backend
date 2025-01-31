package com.example.chulgunhazabackend.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // INFO: Domain Queue
    private static final String CHAT_QUEUE_NAME = "chulgunhazabackend_chat_queue";
    private static final String MAIN_QUEUE_NAME = "chulgunhazabackend_record_queue";

    // INFO: Notification Queue
    private static final String CHAT_NOTIFICATION_QUEUE_NAME = "chulgunhazabackend_notification_queue";
    private static final String MAIN_NOTIFICATION_QUEUE_NAME = "chulgunhazabackend_record_notification_queue";


    // INFO: Exchange
    private static final String CHAT_EXCHANGE_NAME = "chulgunhazabackend_chat";
    private static final String MAIN_EXCHANGE_NAME = "chulgunhazabackend_main";

    // INFO: Routing Key
    private static final String CHAT_ROUTING_KEY = "chat_queue_key";
    private static final String CHAT_NOTIFICATION_ROUTING_KEY = "chat_notification_queue_key";
    private static final String MAIN_ROUTING_KEY = "main_queue_key";
    private static final String MAIN_NOTIFICATION_ROUTING_KEY = "main_notification_queue_key";


    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE_NAME,true);
    }

    @Bean
    public Queue chatNotificationQueue() {
        return new Queue(CHAT_NOTIFICATION_QUEUE_NAME,true);
    }

    @Bean
    public Queue mainQueue() {
        return new Queue(MAIN_QUEUE_NAME,true);
    }

    @Bean
    public Queue mainNotificationQueue() {
        return new Queue(MAIN_NOTIFICATION_QUEUE_NAME,true);
    }

    @Bean
    public DirectExchange chatExchange() {
        return new DirectExchange(CHAT_EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange mainExchange() {
        return new DirectExchange(MAIN_EXCHANGE_NAME);
    }

    @Bean
    public Binding chatBinding() {
        return BindingBuilder.bind(chatQueue()).to(chatExchange()).with(CHAT_ROUTING_KEY);
    }

    @Bean
    public Binding chatNotificationBinding() {
        return BindingBuilder.bind(chatNotificationQueue()).to(chatExchange()).with(CHAT_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Binding mainBinding() {
        return BindingBuilder.bind(mainQueue()).to(mainExchange()).with(MAIN_ROUTING_KEY);
    }

    @Bean
    public Binding mainNotificationBinding() {
        return BindingBuilder.bind(mainNotificationQueue()).to(mainExchange()).with(MAIN_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    // JSON 변환기
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
