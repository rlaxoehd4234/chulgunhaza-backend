package com.example.chulgunhazabackend.listener;

import com.example.chulgunhazabackend.config.RabbitMQConfig;
import com.example.chulgunhazabackend.dto.attendance.AttendanceCreateRequestDto;
import com.example.chulgunhazabackend.dto.chat.ChatNotificationDto;
import com.example.chulgunhazabackend.exception.employeeException.EmployeeException;
import com.example.chulgunhazabackend.exception.employeeException.EmployeeExceptionType;
import com.example.chulgunhazabackend.service.impl.AttendanceServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AttendanceListener {

    private final AttendanceServiceImpl attendanceService; // 다형성 수정
    private final RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = RabbitMQConfig.ATTENDANCE_QUEUE_NAME, containerFactory = "simpleRabbitListenerContainerFactory")
    public void attendanceConsume(@Payload AttendanceCreateRequestDto attendanceCreateRequestDto,
                                  Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            attendanceService.registerAttendance(attendanceCreateRequestDto);
            channel.basicAck(tag, false); // 성공
            System.out.println("디큐 완료");
        }catch (EmployeeException e){

            System.out.println("확인 : " + e.getMessage());// 메세지를 보내야 함 (출근 등록이 실패하였습니다 : 이유)
            channel.basicNack(tag, false, false); // 큐에 있는 메세지 삭제

        } catch (Exception e) {
            // DLQ 이동
            attendanceCreateRequestDto.setMqFailMessage(e.getMessage());
            rabbitTemplate.convertAndSend(RabbitMQConfig.DLX, RabbitMQConfig.A_DLQ, attendanceCreateRequestDto);
            System.out.println("DLQ 이동 : " + e.getMessage());
        }

    }

}


