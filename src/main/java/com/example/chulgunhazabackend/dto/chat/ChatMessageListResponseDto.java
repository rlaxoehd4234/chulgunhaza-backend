package com.example.chulgunhazabackend.dto.chat;

import com.example.chulgunhazabackend.domain.chat.ChatMessage;
import com.example.chulgunhazabackend.domain.member.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ChatMessageListResponseDto {

    private Long senderId;

    private String message;

    private Long RoomId;

    private LocalDateTime createdAt;

    public ChatMessageListResponseDto fromEntity(ChatMessage chatMessage) {
        return new ChatMessageListResponseDto(
                chatMessage.getEmployee().getId()
                , chatMessage.getMessage()
                , chatMessage.getChatRoom().getId()
                , chatMessage.getCreatedAt()
        );
    }
}
