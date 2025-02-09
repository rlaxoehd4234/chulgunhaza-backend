package com.example.chulgunhazabackend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // INFO: Domain Queue - CHAT
    public static final String CHAT_QUEUE_NAME = "chulgunhazabackend_chat_queue";

    // INFO: Domain Queue - MAIN
    public static final String ATTENDANCE_QUEUE_NAME = "chulgunhazabackend_attendance_queue";
    public static final String LEAVE_WORK_QUEUE_NAME = "chulgunhazabackend_leave_work_queue";

    // INFO: Notification Queue - CHAT
    public static final String CHAT_NOTIFICATION_QUEUE_NAME = "chulgunhazabackend_notification_queue";

    // INFO: Notification Queue - MAIN
    public static final String MAIN_NOTIFICATION_QUEUE_NAME = "chulgunhazabackend_record_notification_queue";


    // INFO: Exchange - Chat
    public static final String CHAT_EXCHANGE_NAME = "chulgunhazabackend_chat";

    // INFO: Exchange - Main
    public static final String MAIN_EXCHANGE_NAME = "chulgunhazabackend_main";

    // INFO: Routing Key - Chat
    public static final String CHAT_ROUTING_KEY = "chat_queue_key";
    public static final String CHAT_NOTIFICATION_ROUTING_KEY = "chat_notification_queue_key";

    // INFO: Routing Key - Main
    public static final String ATTENDANCE_ROUTING_KEY = "attendance_queue_key";
    public static final String LEAVE_WORK_ROUTING_KEY = "leave_work_queue_key";
    public static final String MAIN_NOTIFICATION_ROUTING_KEY = "main_notification_queue_key";

    // Dead - Letter
    public static final String DLX = "deadLetterExchange";
    public static final String A_DLQ = "attendanceDeadLetterQueue";



    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE_NAME,true);
    }

    @Bean
    public Queue chatNotificationQueue() {
        return new Queue(CHAT_NOTIFICATION_QUEUE_NAME,true);
    }

    @Bean
    public Queue attendanceQueue() {
        return QueueBuilder.durable(ATTENDANCE_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", A_DLQ)
                .build();
    }

    @Bean
    public Queue leaveWorkQueue() {
        return new Queue(LEAVE_WORK_QUEUE_NAME,true);
    }

    @Bean
    public Queue mainNotificationQueue() {
        return new Queue(MAIN_NOTIFICATION_QUEUE_NAME,true);
    }

    @Bean
    public Queue attendanceDeadLetterQueue(){
        return QueueBuilder.durable(A_DLQ).build();
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
    public TopicExchange deadLetterExchange(){
        return new TopicExchange(DLX);
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
    public Binding mainAttendanceBinding() {
        return BindingBuilder.bind(attendanceQueue()).to(mainExchange()).with(ATTENDANCE_ROUTING_KEY);
    }
    @Bean
    public Binding mainLeaveWorkBinding() {
        return BindingBuilder.bind(leaveWorkQueue()).to(mainExchange()).with(LEAVE_WORK_ROUTING_KEY);
    }

    @Bean
    public Binding mainNotificationBinding() {
        return BindingBuilder.bind(mainNotificationQueue()).to(mainExchange()).with(MAIN_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Binding attendanceDeadLetterBinding() {
        return BindingBuilder.bind(attendanceDeadLetterQueue()).to(deadLetterExchange()).with(A_DLQ);
    }

    // 메시지 송신 빈 등록
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    // 메세지 수신 빈 등록
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory listenerContainerFactory
                = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setDefaultRequeueRejected(false);
        listenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        listenerContainerFactory.setMessageConverter(jsonMessageConverter());

        return listenerContainerFactory;
    }


    // JSON 변환기
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
