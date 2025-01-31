package com.example.chulgunhazabackend.service.impl;

import com.example.chulgunhazabackend.dto.chat.ChatNotificationDto;
import com.example.chulgunhazabackend.service.ChatRabbitMQMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRabbitMQMessageServiceImpl implements ChatRabbitMQMessageService {

    private final RabbitTemplate rabbitTemplate;

    //INFO : 채팅 알람 발생 시 RMQ 로 메시지 전송
    public void sendNotification(ChatNotificationDto chatNotificationDto){
        String routingKey = "chat_notification_queue_key";
        rabbitTemplate.convertAndSend("chulgunhazabackend_chat", routingKey, chatNotificationDto);
        log.info("Sending notification to RabbitMQ");
    }

}
