package com.example.chulgunhazabackend.event.employee.event;

import com.example.chulgunhazabackend.event.common.Event;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class EmployeeModifyEvent extends Event {

    // 이벤트 메세지
    private final String message;

    public EmployeeModifyEvent(Long target, Long host) {
        super(target, host);
        this.message = target + "님의 정보가 수정되었습니다.";
    }
}
