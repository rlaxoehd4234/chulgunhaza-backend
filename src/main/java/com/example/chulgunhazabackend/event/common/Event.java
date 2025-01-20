package com.example.chulgunhazabackend.event.common;

import lombok.Getter;

@Getter
public abstract class Event {

    // 이벤트 발행 시간
    private final long timestamp;

    // 진행자 사원번호
    private final Long employeeNumber;

    // 대상 사원번호
    private final Long target;

    public Event(Long target, Long employeeNumber) {
        this.timestamp = System.currentTimeMillis();
        this.target = target;
        this.employeeNumber = employeeNumber;
    }
}
