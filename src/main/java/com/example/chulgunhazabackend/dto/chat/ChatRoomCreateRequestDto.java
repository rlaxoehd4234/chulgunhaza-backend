package com.example.chulgunhazabackend.dto.chat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatRoomCreateRequestDto {

    @NotNull(message = "전송자 아이디가 누락되었습니다.")
    private Long senderId;

    @NotNull(message = "수신자 아이디가 누락되었습니다.")
    private Long receiverId;

}
