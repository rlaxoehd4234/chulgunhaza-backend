package com.example.chulgunhazabackend.dto.chat;

import com.example.chulgunhazabackend.domain.chat.ChatRoom;
import com.example.chulgunhazabackend.domain.chat.EmployeeChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ChatRoomListResponseDto {

    private Long EmployeeChatRoomId;

    private Long userId;

    private String userName;

    public ChatRoomListResponseDto fromEntity(EmployeeChatRoom employeeChatRoom) {
        return new ChatRoomListResponseDto(employeeChatRoom.getId(), employeeChatRoom.getEmployee().getEmployeeNo(), employeeChatRoom.getEmployee().getName());
    }
}
