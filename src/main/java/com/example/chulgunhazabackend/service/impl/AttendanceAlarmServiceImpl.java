package com.example.chulgunhazabackend.service.impl;

import com.example.chulgunhazabackend.service.sse.SseEmitterManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceAlarmServiceImpl {

    private final SseEmitterManager sseEmitterManager;
    public SseEmitter subscribe(Long employeeNo) throws IOException {
        return sseEmitterManager.mainRegisterEmitter(employeeNo);
    }


}
