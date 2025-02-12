package com.example.chulgunhazabackend.dto.chat;

import com.example.chulgunhazabackend.domain.chat.ChatMessage;
import com.example.chulgunhazabackend.domain.chat.ChatRoom;
import com.example.chulgunhazabackend.domain.member.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageCreateRMQDto {

    private Long senderId;

    private Long receiverId;

    private String message;

    private Long roomId;


    public ChatMessage toEntity(ChatRoom chatRoom, Employee employee){
        return ChatMessage.builder()
                .employee(employee)
                .chatRoom(chatRoom)
                .message(message)
                .build();
    }
}
