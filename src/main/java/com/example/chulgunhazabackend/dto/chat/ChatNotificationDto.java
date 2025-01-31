package com.example.chulgunhazabackend.dto.chat;

import com.example.chulgunhazabackend.domain.member.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotificationDto implements Serializable {

    public Long employeeNo;

    public String name;

    public String department;

    public Position position;

    public String message;

    // TODO: 채팅 방 찾는 링크 추가

}
