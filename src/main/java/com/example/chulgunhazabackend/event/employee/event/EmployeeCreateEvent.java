package com.example.chulgunhazabackend.event.employee.event;

import com.example.chulgunhazabackend.event.common.Event;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmployeeCreateEvent extends Event {

    private final String message;
    public EmployeeCreateEvent(Long target, Long employeeNumber) {
        super(target, employeeNumber);
        this.message = target + " 사원이 생성되었습니다.";
    }
}
