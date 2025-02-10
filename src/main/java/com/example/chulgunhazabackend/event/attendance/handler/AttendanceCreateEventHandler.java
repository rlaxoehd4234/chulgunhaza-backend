package com.example.chulgunhazabackend.event.attendance.handler;

import com.example.chulgunhazabackend.event.attendance.event.AttendanceCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class AttendanceCreateEventHandler {

    @Async
    @TransactionalEventListener(
            classes = AttendanceCreateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleAttendanceCreateEventAfterCommit(AttendanceCreateEvent attendanceCreateEvent){
        System.out.println("이벤트 등록 : " + attendanceCreateEvent);
    }

}
