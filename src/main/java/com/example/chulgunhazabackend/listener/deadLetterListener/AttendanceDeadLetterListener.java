package com.example.chulgunhazabackend.listener.deadLetterListener;

import com.example.chulgunhazabackend.config.RabbitMQConfig;
import com.example.chulgunhazabackend.dto.attendance.AttendanceCreateRequestDto;
import com.example.chulgunhazabackend.service.impl.AttendanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceDeadLetterListener {

    private final AttendanceServiceImpl attendanceService;
    @RabbitListener(queues = RabbitMQConfig.A_DLQ, containerFactory = "simpleRabbitListenerContainerFactory")
    @Retryable(
            retryFor = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000),
            recover = "attendanceDLQConsumeRecover"
    )
    public void attendanceDlqConsume(@Payload AttendanceCreateRequestDto attendanceCreateRequestDto) throws Exception {
        System.out.println(attendanceCreateRequestDto);
        attendanceService.registerAttendance(attendanceCreateRequestDto);
        System.out.println("출근 - DLQ 디큐 완료");
    }

    @Recover
    public void attendanceDLQConsumeRecover(Exception e, AttendanceCreateRequestDto attendanceCreateRequestDto){
        System.out.println("DLQ 리커버 메시지 : " + e.getMessage());
        // 어드민 알람으로 발행
    }

}
